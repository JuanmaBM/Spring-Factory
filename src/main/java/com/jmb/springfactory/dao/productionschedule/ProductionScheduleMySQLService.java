package com.jmb.springfactory.dao.productionschedule;

import java.util.stream.Stream;

import com.jmb.springfactory.dao.GenericMySQLService;
import com.jmb.springfactory.model.entity.ProductionSchedule;

public interface ProductionScheduleMySQLService extends GenericMySQLService<ProductionSchedule, Integer> {

  public Stream<ProductionSchedule> findAllByExample(final ProductionSchedule queryObject);
}
