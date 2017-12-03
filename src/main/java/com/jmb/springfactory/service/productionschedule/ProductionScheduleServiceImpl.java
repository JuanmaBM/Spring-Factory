package com.jmb.springfactory.service.productionschedule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jmb.springfactory.dao.GenericMySQLService;
import com.jmb.springfactory.dao.productionschedule.ProductionScheduleMySQLService;
import com.jmb.springfactory.exceptions.ServiceLayerException;
import com.jmb.springfactory.model.bo.BusinessObjectBase;
import com.jmb.springfactory.model.dto.ProductionScheduleDto;
import com.jmb.springfactory.model.entity.ProductionSchedule;
import com.jmb.springfactory.model.enumeration.ProductionScheduleStateEnum;
import com.jmb.springfactory.service.GenericServiceImpl;

@Service
public class ProductionScheduleServiceImpl extends GenericServiceImpl<ProductionSchedule, ProductionScheduleDto,
  BusinessObjectBase, Integer> implements ProductionScheduleService {

  @Autowired
  private ProductionScheduleMySQLService productionScheduleMySQLService;

  @Override
  public GenericMySQLService<ProductionSchedule, Integer> genericDao() {
    return productionScheduleMySQLService;
  }

  @Override
  public Class<? extends ProductionSchedule> getClazz() {
    return ProductionSchedule.class;
  }

  @Override
  public Class<? extends ProductionScheduleDto> getDtoClazz() {
    return ProductionScheduleDto.class;
  }

  @Override
  public Class<? extends BusinessObjectBase> getBoClazz() {
    return BusinessObjectBase.class;
  }
  
  @Override
  public ProductionScheduleDto save(ProductionScheduleDto schedule) throws ServiceLayerException {
    schedule.setState(ProductionScheduleStateEnum.OPEN.name());
    return super.save(schedule);
  }

}
