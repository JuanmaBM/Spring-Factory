package com.jmb.springfactory.service.productionorder;

import java.util.List;

import com.jmb.springfactory.exceptions.NotFoundException;
import com.jmb.springfactory.exceptions.ServiceLayerException;
import com.jmb.springfactory.model.dto.ProductionOrderDTO;
import com.jmb.springfactory.service.GenericService;

public interface ProductionOrderService extends GenericService<ProductionOrderDTO, Integer> {

  /**
   * Find all order defined into a schedule indentified with param idSchedule
   * @param idSchedule
   * @return
   */
  public List<ProductionOrderDTO> findAllBySchedule(Integer idSchedule) throws NotFoundException;

  /**
   * Find the order identified with idOrder into schedule identified with idSchedule
   * @param idSchedule
   * @param idOrder
   * @return
   * @throws NotFoundException
   */
  public ProductionOrderDTO findOneByIdInSchedule(Integer idSchedule, Integer idOrder) throws NotFoundException;
  
  /**
   * Creates a new order and add it in the order list from schedule
   * @param order
   * @param idSchedule
   * @return
   */
  public ProductionOrderDTO save(final ProductionOrderDTO order, Integer idSchedule) throws ServiceLayerException, 
    NotFoundException;

}
