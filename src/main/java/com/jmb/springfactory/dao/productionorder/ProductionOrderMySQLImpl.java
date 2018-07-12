package com.jmb.springfactory.dao.productionorder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.jmb.springfactory.dao.GenericMySQLServiceImpl;
import com.jmb.springfactory.dao.group.GroupMongoService;
import com.jmb.springfactory.dao.productionschedule.ProductionScheduleMySQLService;
import com.jmb.springfactory.exceptions.NotFoundException;
import com.jmb.springfactory.exceptions.PersistenceLayerException;
import com.jmb.springfactory.model.entity.ProductionOrder;
import com.jmb.springfactory.model.entity.WorkGroup;
import com.jmb.springfactory.model.enumeration.StatusEnum;
import com.jmb.springfactory.service.UtilsService;

import lombok.val;

@Service
public class ProductionOrderMySQLImpl extends GenericMySQLServiceImpl<ProductionOrder, Integer>
        implements ProductionOrderMySQL {

    @Autowired
    private ProductionOrderRepository productionOrderRepository;

    @Autowired
    private ProductionScheduleMySQLService productionScheduleMySQLService;

    @Autowired
    private GroupMongoService groupMongoService;

    @Override
    public JpaRepository<ProductionOrder, Integer> getRepository() {
        return productionOrderRepository;
    }

    @Override
    public Stream<ProductionOrder> findAllByScheduleId(final Integer scheduleId) {
        return productionOrderRepository.findByProductionSchedule_Id(scheduleId).stream();
    }

    @Override
    public ProductionOrder save(ProductionOrder order, Integer scheduleId)
            throws PersistenceLayerException, NotFoundException {

        final ProductionOrder orderSaved;

        if (UtilsService.existAll(scheduleId, order)) {
            val schedule = productionScheduleMySQLService.findOne(scheduleId).orElseThrow(NotFoundException::new);
            order.setProductionSchedule(schedule);
            orderSaved = this.save(order);
        } else {
            throw new IllegalArgumentException("Production order and schedule id must be not null");
        }

        return orderSaved;
    }

    @Override
    public Stream<ProductionOrder> findAllByScheduleIdAndGroupIdAndStatus(final Integer groupId,
            final StatusEnum status) {

        Function<Integer, Stream<ProductionOrder>> findByGroupId = id -> Optional.ofNullable(id)
                .flatMap(groupMongoService::findOne).map(WorkGroup::getOrdersAssigned).map(List::stream)
                .orElse(Stream.empty());

        final Function<Stream<ProductionOrder>, Stream<ProductionOrder>> filterByStatus = orders -> orders
                .filter(order -> status.equals(order.getStatus()));

        if (UtilsService.exist(status)) {
            findByGroupId = findByGroupId.andThen(filterByStatus);
        }

        return findByGroupId.apply(groupId);
    }

}
