package com.jmb.springfactory.dao.productionschedule;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.jmb.springfactory.dao.GenericMySQLService;
import com.jmb.springfactory.model.entity.ProductionSchedule;

public interface ProductionScheduleMySQLService extends GenericMySQLService<ProductionSchedule, Integer> {

  Page<ProductionSchedule> findAllByExample(final ProductionSchedule queryObject, final Pageable paginator);
  Page<ProductionSchedule> findAll(Pageable paginator);

}
