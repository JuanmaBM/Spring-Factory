package com.jmb.springfactory.dao.productionschedule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jmb.springfactory.dao.GenericMySQLServiceImpl;
import com.jmb.springfactory.model.entity.ProductionSchedule;

@Repository
public class ProductionScheduleMySQLServiceImpl extends GenericMySQLServiceImpl<ProductionSchedule, Integer> 
  implements ProductionScheduleMySQLService {

  @Autowired
  private ProductionScheduleRepository productionScheduleRepository;

  @Override
  public JpaRepository<ProductionSchedule, Integer> getRepository() {
    return productionScheduleRepository;
  }

}
