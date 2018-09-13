package com.jmb.springfactory.dao.productionschedule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.GenericPropertyMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

  @Override
  public Page<ProductionSchedule> findAll(final Pageable paginator) {
      return productionScheduleRepository.findAll(paginator);
  }
  
  @Override
  public Page<ProductionSchedule> findAllByExample(final ProductionSchedule queryObject, final Pageable paginator) {
      final Example<ProductionSchedule> example = Example.of(queryObject, createMatcher());
      return productionScheduleRepository.findAll(example, paginator);
  }
  
  private ExampleMatcher createMatcher() {
      return ExampleMatcher.matching().withMatcher("name", GenericPropertyMatcher::contains);
  }

}
