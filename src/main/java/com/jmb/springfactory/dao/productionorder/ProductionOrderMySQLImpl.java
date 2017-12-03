package com.jmb.springfactory.dao.productionorder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.jmb.springfactory.dao.GenericMySQLServiceImpl;
import com.jmb.springfactory.model.entity.ProductionOrder;

@Service
public class ProductionOrderMySQLImpl extends GenericMySQLServiceImpl<ProductionOrder, Integer> 
  implements ProductionOrderMySQL {

  @Autowired
  private ProductionOrderRepository productionOrderRepository;
  
  @Override
  public JpaRepository<ProductionOrder, Integer> getRepository() {
    return productionOrderRepository;
  }

}
