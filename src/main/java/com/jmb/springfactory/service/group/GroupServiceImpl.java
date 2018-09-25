package com.jmb.springfactory.service.group;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.jmb.springfactory.dao.GenericMySQLService;
import com.jmb.springfactory.dao.group.GroupMongoService;
import com.jmb.springfactory.exceptions.NotFoundException;
import com.jmb.springfactory.exceptions.ServiceLayerException;
import com.jmb.springfactory.model.bo.BusinessObjectBase;
import com.jmb.springfactory.model.dto.ProductionOrderDTO;
import com.jmb.springfactory.model.dto.WorkGroupDto;
import com.jmb.springfactory.model.entity.WorkGroup;
import com.jmb.springfactory.service.GenericServiceImpl;
import com.jmb.springfactory.service.ValidatorService;
import com.jmb.springfactory.service.productionorder.ProductionOrderService;

@Service
public class GroupServiceImpl extends GenericServiceImpl<WorkGroup, WorkGroupDto, BusinessObjectBase, Integer>
        implements GroupService {

    @Autowired
    private GroupMongoService groupMongoService;

    @Autowired
    @Qualifier("groupValidatorService")
    private ValidatorService groupValidatorService;

    @Autowired
    private ProductionOrderService productionOrderService;

    @Override
    public GenericMySQLService<WorkGroup, Integer> genericDao() {
        return groupMongoService;
    }

    @Override
    public WorkGroupDto save(WorkGroupDto group) throws ServiceLayerException {
        groupValidatorService.validateOnCreate(group);
        return super.save(group);
    }

    @Override
    public Class<? extends WorkGroup> getClazz() {
        return WorkGroup.class;
    }

    @Override
    public Class<? extends WorkGroupDto> getDtoClazz() {
        return WorkGroupDto.class;
    }

    @Override
    public Class<? extends BusinessObjectBase> getBoClazz() {
        return BusinessObjectBase.class;
    }

    @Override
    public List<WorkGroupDto> findAllByOrderId(Integer orderId) {

        final Function<Integer, ProductionOrderDTO> findOrderById = id -> {
            ProductionOrderDTO orderDto = null;
            try {
                orderDto = productionOrderService.findOne(orderId);
            } catch (NotFoundException e) {
                orderDto = null;
                serviceLog.warn(String.format("Order with id %s not found", orderId));
            }
            return orderDto;
        };

        return Optional.ofNullable(orderId).map(findOrderById).map(ProductionOrderDTO::getGroupsAssigned)
                .orElse(Collections.emptyList());
    }

}
