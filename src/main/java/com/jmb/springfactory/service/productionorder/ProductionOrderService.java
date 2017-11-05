package com.jmb.springfactory.service.productionorder;

import java.util.List;

import com.jmb.springfactory.model.dto.ProductionOrderDTO;
import com.jmb.springfactory.service.GenericService;

public interface ProductionOrderService extends GenericService<ProductionOrderDTO, Integer> {

  /**
   * Find all order defined into a schedule indentified with param idSchedule
   * @param idSchedule
   * @return
   */
  public List<ProductionOrderDTO> findAllBySchedule(Integer idSchedule);
}
