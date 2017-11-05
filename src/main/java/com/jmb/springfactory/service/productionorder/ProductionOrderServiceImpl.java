package com.jmb.springfactory.service.productionorder;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jmb.springfactory.dao.GenericMySQLService;
import com.jmb.springfactory.dao.productionorder.ProductionOrderMySQL;
import com.jmb.springfactory.exceptions.NotFoundException;
import com.jmb.springfactory.exceptions.ServiceLayerException;
import com.jmb.springfactory.model.bo.BusinessObjectBase;
import com.jmb.springfactory.model.dto.ProductionOrderDTO;
import com.jmb.springfactory.model.dto.ProductionScheduleDto;
import com.jmb.springfactory.model.entity.ProductionOrder;
import com.jmb.springfactory.service.GenericServiceImpl;
import static com.jmb.springfactory.service.UtilsService.*;
import com.jmb.springfactory.service.productionschedule.ProductionScheduleService;

@Service
public class ProductionOrderServiceImpl extends GenericServiceImpl<ProductionOrder, ProductionOrderDTO,
  BusinessObjectBase, Integer> implements ProductionOrderService {

  @Autowired 
  private ProductionOrderMySQL productionOrderMySQL;
  
  @Autowired
  private ProductionScheduleService productionScheduleService;

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
  public List<ProductionOrderDTO> findAllBySchedule(Integer idSchedule) throws NotFoundException {
    return Optional.ofNullable(productionScheduleService.findOne(idSchedule))
        .map(ProductionScheduleDto::getOrders)
        .orElseThrow(NotFoundException::new);
  }

  @Override
  public ProductionOrderDTO findOneByIdInSchedule(Integer idSchedule, Integer idOrder) throws NotFoundException {
    return findAllBySchedule(idSchedule).parallelStream()
        .filter(order -> order.getId().equals(idOrder))
        .findFirst()
        .orElseThrow(NotFoundException::new);
  }
  
  @Override
  public ProductionOrderDTO save(final ProductionOrderDTO order, Integer idSchedule) throws ServiceLayerException, 
    NotFoundException {

    final ProductionOrderDTO newOrder = this.save(order);
    logCreatedEntity(newOrder, serviceLog);
    
    serviceLog.info("Updating schedule's orders");
    updateOrderListFromSchedule(order, idSchedule);
    
    return newOrder;
  }

  @SuppressWarnings("unchecked")
  private void updateOrderListFromSchedule(final ProductionOrderDTO order, Integer idSchedule)
      throws NotFoundException, ServiceLayerException {

    final ProductionScheduleDto schedule = productionScheduleService.findOne(idSchedule);

    schedule.setOrders((List<ProductionOrderDTO>) addOrCreateIfNotExist(schedule.getOrders(), order));
    productionScheduleService.update(schedule, idSchedule);
  }

}
