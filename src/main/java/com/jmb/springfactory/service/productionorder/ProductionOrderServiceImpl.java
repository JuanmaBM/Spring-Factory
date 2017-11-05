package com.jmb.springfactory.service.productionorder;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.jmb.springfactory.dao.GenericMySQLService;
import com.jmb.springfactory.dao.productionorder.ProductionOrderMySQL;
import com.jmb.springfactory.model.bo.BusinessObjectBase;
import com.jmb.springfactory.model.dto.ProductionOrderDTO;
import com.jmb.springfactory.model.entity.ProductionOrder;
import com.jmb.springfactory.service.GenericServiceImpl;

public class ProductionOrderServiceImpl extends GenericServiceImpl<ProductionOrder, ProductionOrderDTO,
  BusinessObjectBase, Integer> implements ProductionOrderService {

  @Autowired 
  private ProductionOrderMySQL productionOrderMySQL;

  @Override
  public GenericMySQLService<ProductionOrder, Integer> genericDao() {
    return productionOrderMySQL;
  }

  @Override
  public Class<? extends ProductionOrder> getClazz() {
    return ProductionOrder.class;
  }

  @Override
  public Class<? extends ProductionOrderDTO> getDtoClazz() {
    return ProductionOrderDTO.class;
  }

  @Override
  public Class<? extends BusinessObjectBase> getBoClazz() {
    return BusinessObjectBase.class;
  }
  
  @Override
  public List<ProductionOrderDTO> findAllBySchedule(Integer idSchedule) {
    return null;
  }

}
