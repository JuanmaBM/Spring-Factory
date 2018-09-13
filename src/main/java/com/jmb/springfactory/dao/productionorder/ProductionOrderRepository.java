package com.jmb.springfactory.dao.productionorder;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.jmb.springfactory.model.entity.ProductionOrder;
import com.jmb.springfactory.model.enumeration.StatusEnum;

@Repository
public interface ProductionOrderRepository extends JpaRepository<ProductionOrder, Integer> {
    
    List<ProductionOrder> findByProductionSchedule_Id(Integer scheduleId);
    List<ProductionOrder> findByGroupsAssigned_IdAndStatus(Integer groupId, StatusEnum status);

}
