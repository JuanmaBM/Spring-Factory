package com.jmb.springfactory.service.productionorder;

import static com.jmb.springfactory.service.UtilsService.logCreatedEntity;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jmb.springfactory.dao.GenericMySQLService;
import com.jmb.springfactory.dao.group.GroupMongoService;
import com.jmb.springfactory.dao.productionorder.ProductionOrderMySQL;
import com.jmb.springfactory.exceptions.NotFoundException;
import com.jmb.springfactory.exceptions.PersistenceLayerException;
import com.jmb.springfactory.exceptions.ServiceLayerException;
import com.jmb.springfactory.model.bo.BusinessObjectBase;
import com.jmb.springfactory.model.dto.ProductionOrderDTO;
import com.jmb.springfactory.model.dto.WorkGroupDto;
import com.jmb.springfactory.model.entity.ProductionOrder;
import com.jmb.springfactory.model.entity.WorkGroup;
import com.jmb.springfactory.model.enumeration.Measurements;
import com.jmb.springfactory.model.enumeration.StatusEnum;
import com.jmb.springfactory.service.GenericServiceImpl;
import com.jmb.springfactory.service.UtilsService;
import com.jmb.springfactory.service.productionschedule.ProductionScheduleService;

@Service
public class ProductionOrderServiceImpl
        extends GenericServiceImpl<ProductionOrder, ProductionOrderDTO, BusinessObjectBase, Integer>
        implements ProductionOrderService {

    @Autowired
    private ProductionOrderMySQL productionOrderMySQL;

    @Autowired
    private ProductionScheduleService productionScheduleService;

    @Autowired
    private GroupMongoService groupMongoService;

    @Override
    public GenericMySQLService<ProductionOrder, Integer> genericDao() {
        return productionOrderMySQL;
    }

    @Override
    public Class<? extends ProductionOrder> getClazz() {
        return ProductionOrder.class;
    }

    @Override
    public Class<? extends ProductionOrderDTO> getDtoClazz() {
        return ProductionOrderDTO.class;
    }

    @Override
    public Class<? extends BusinessObjectBase> getBoClazz() {
        return BusinessObjectBase.class;
    }

    @Override
    public List<ProductionOrderDTO> findAllBySchedule(Integer idSchedule) throws NotFoundException {
        return productionOrderMySQL.findAllByScheduleId(idSchedule).map(this::entityToDto).collect(Collectors.toList());
    }

    @Override
    public ProductionOrderDTO findOneByIdInSchedule(Integer idSchedule, Integer idOrder) throws NotFoundException {
        return findAllBySchedule(idSchedule).parallelStream().filter(order -> order.getId().equals(idOrder)).findFirst()
                .orElseThrow(NotFoundException::new);
    }

    @Override
    public ProductionOrderDTO save(final ProductionOrderDTO order, Integer idSchedule)
            throws ServiceLayerException, NotFoundException {

        final Function<ProductionOrder, ProductionOrder> saveOrderWithSchedule = orderEntity -> {
            ProductionOrder storedEntity = null;
            try {
                serviceLog.debug("Set status order as OPEN");
                orderEntity.setStatus(StatusEnum.OPEN);
                storedEntity = productionOrderMySQL.save(orderEntity, idSchedule);
            } catch (PersistenceLayerException e) {
                serviceLog.error(String.format("Database error: %s", e.getMessage()));
            } catch (NotFoundException e) {
                serviceLog.error(String.format("Schedule with id %s not found", idSchedule));
            }
            return storedEntity;
        };

        final ProductionOrderDTO newOrder = Optional.ofNullable(order).map(this::dtoToEntity).map(saveOrderWithSchedule)
                .map(this::entityToDto).orElseThrow(ServiceLayerException::new);
        logCreatedEntity(newOrder, serviceLog);

        return newOrder;
    }

    @Override
    public ProductionOrder merge(ProductionOrderDTO orderDto, ProductionOrder order) {

        if (UtilsService.existAll(orderDto, order)) {

            serviceLog.info("Assign groups into order");
            mergeGroupsAssigned(orderDto, order);

            serviceLog.info("Merge order details");
            mergeOrderDetails(orderDto, order);
        }
        
        serviceLog.info("Merge process finished");
        serviceLog.debug(String.format("Merged order: [%s]", order.toString()));
        return order;
    }

    private void mergeOrderDetails(ProductionOrderDTO orderDto, ProductionOrder order) {

        order.setName(orderDto.getName());
        order.setDescription(orderDto.getDescription());
        Optional.ofNullable(orderDto.getMeasurements()).map(Measurements::valueOf).ifPresent(order::setMeasurements);
        Optional.ofNullable(orderDto.getStatus()).map(StatusEnum::valueOf).ifPresent(order::setStatus);
    }

    private void mergeGroupsAssigned(ProductionOrderDTO orderDto, ProductionOrder order) {

        final List<WorkGroup> groupsAssigned = Optional.ofNullable(orderDto).map(ProductionOrderDTO::getGroupsAssigned)
                .orElse(Collections.emptyList()).stream().map(WorkGroupDto::getId).map(groupMongoService::findOne)
                .map(Optional::get).collect(Collectors.toList());

        order.setGroupsAssigned(groupsAssigned);
    }

    @Override
    public List<ProductionOrderDTO> findAllByGroup(Integer idSchedule, Integer groupId, StatusEnum status) {
        return productionOrderMySQL.findAllByScheduleIdAndGroupIdAndStatus(groupId, status).map(this::entityToDto)
                .collect(Collectors.toList());
    }

}
