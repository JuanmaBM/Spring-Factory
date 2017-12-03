package com.jmb.springfactory.dao.productionorder;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jmb.springfactory.model.entity.ProductionOrder;

@Repository
public interface ProductionOrderRepository extends JpaRepository<ProductionOrder, Integer> {

}
