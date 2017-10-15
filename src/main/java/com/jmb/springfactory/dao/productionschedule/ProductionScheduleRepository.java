package com.jmb.springfactory.dao.productionschedule;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jmb.springfactory.model.entity.ProductionSchedule;

public interface ProductionScheduleRepository extends JpaRepository<ProductionSchedule, Integer> {

}
