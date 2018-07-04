package com.jmb.springfactory.service.productionorder;

import static com.jmb.springfactory.service.UtilsService.logCreatedEntity;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jmb.springfactory.dao.GenericMySQLService;
import com.jmb.springfactory.dao.productionorder.ProductionOrderMySQL;
import com.jmb.springfactory.exceptions.NotFoundException;
import com.jmb.springfactory.exceptions.PersistenceLayerException;
import com.jmb.springfactory.exceptions.ServiceLayerException;
import com.jmb.springfactory.model.bo.BusinessObjectBase;
import com.jmb.springfactory.model.dto.ProductionOrderDTO;
import com.jmb.springfactory.model.entity.ProductionOrder;
import com.jmb.springfactory.service.GenericServiceImpl;
import com.jmb.springfactory.service.productionschedule.ProductionScheduleService;

@Service
public class ProductionOrderServiceImpl
        extends GenericServiceImpl<ProductionOrder, ProductionOrderDTO, BusinessObjectBase, Integer>
        implements ProductionOrderService {

    @Autowired
    private ProductionOrderMySQL productionOrderMySQL;

    @Autowired
    private ProductionScheduleService productionScheduleService;

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

        final Function<ProductionOrder, ProductionOrder> saveOrderWithSchedule = orderEntity ->{
            ProductionOrder storedEntity = null;
            try {
              storedEntity = productionOrderMySQL.save(orderEntity, idSchedule);
            } catch (PersistenceLayerException e) {
              serviceLog.error(String.format("Database error: %s", e.getMessage()));
            } catch (NotFoundException e) {
                serviceLog.error(String.format("Schedule with id %s not found", idSchedule));
            }
            return storedEntity;
        };

        final ProductionOrderDTO newOrder = Optional.ofNullable(order)
            .map(this::dtoToEntity)
            .map(saveOrderWithSchedule)
            .map(this::entityToDto)
            .orElseThrow(ServiceLayerException::new);
        logCreatedEntity(newOrder, serviceLog);

        return newOrder;
    }

}
