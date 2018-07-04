package com.jmb.springfactory.dao.productionorder;

import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.jmb.springfactory.dao.GenericMySQLServiceImpl;
import com.jmb.springfactory.dao.productionschedule.ProductionScheduleMySQLService;
import com.jmb.springfactory.exceptions.NotFoundException;
import com.jmb.springfactory.exceptions.PersistenceLayerException;
import com.jmb.springfactory.model.entity.ProductionOrder;
import com.jmb.springfactory.service.UtilsService;

import lombok.val;

@Service
public class ProductionOrderMySQLImpl extends GenericMySQLServiceImpl<ProductionOrder, Integer>
        implements ProductionOrderMySQL {

    @Autowired
    private ProductionOrderRepository productionOrderRepository;

    @Autowired
    private ProductionScheduleMySQLService productionScheduleMySQLService;

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

}
