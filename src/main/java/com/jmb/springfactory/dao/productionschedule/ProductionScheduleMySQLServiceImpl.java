package com.jmb.springfactory.dao.productionschedule;

import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.GenericPropertyMatcher;
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
  
  public Stream<ProductionSchedule> findAllByExample(final ProductionSchedule queryObject) {
      final Example<ProductionSchedule> example = Example.of(queryObject, createMatcher());
      return productionScheduleRepository.findAll(example).stream();
  }
  
  private ExampleMatcher createMatcher() {
      return ExampleMatcher.matching().withMatcher("name", GenericPropertyMatcher::contains);
  }

}
