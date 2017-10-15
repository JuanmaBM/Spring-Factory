package com.jmb.springfactory.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jmb.springfactory.controller.GenericController;
import com.jmb.springfactory.model.dto.ProductionScheduleDto;
import com.jmb.springfactory.service.GenericService;
import com.jmb.springfactory.service.productionschedule.ProductionScheduleService;

@RestController
@RequestMapping("/schedule")
public class ProductionScheduleController extends GenericController<ProductionScheduleDto, Integer> {

  @Autowired
  private ProductionScheduleService productionScheduleService;

  @Override
  public GenericService<ProductionScheduleDto, Integer> genericService() {
    return productionScheduleService;
  }

}
