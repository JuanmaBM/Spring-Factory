package com.jmb.springfactory.dao.productionorder;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.jmb.springfactory.model.entity.ProductionOrder;

@Repository
public interface ProductionOrderRepository extends JpaRepository<ProductionOrder, Integer> {
    
    List<ProductionOrder> findByProductionSchedule_Id(Integer scheduleId);

}
