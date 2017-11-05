package com.jmb.springfactory.controller.api;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jmb.springfactory.exceptions.NotFoundException;
import com.jmb.springfactory.model.dto.ProductionOrderDTO;
import com.jmb.springfactory.service.productionorder.ProductionOrderService;

@RestController
@RequestMapping("/schedule/{idSchedule}/order")
public class ProducionOrderController {
  
  @Autowired
  private ProductionOrderService productionOrderService;

  @GetMapping
  public List<ProductionOrderDTO> findAll(@PathVariable("idSchedule") Integer idSchedule) throws NotFoundException {
    return productionOrderService.findAllBySchedule(idSchedule);
  }
  
  @GetMapping("/{idOrder}")
  public ProductionOrderDTO findById(@PathVariable("idSchedule") Integer idSchedule, 
      @PathVariable("idOrder") Integer idOrder) throws NotFoundException {
    return productionOrderService.findOneByIdInSchedule(idSchedule, idOrder);
  }
  
  @PostMapping
  public ProductionOrderDTO create(@Valid @RequestBody ProductionOrderDTO order, 
      @PathVariable("idSchedule") Integer idSchedule) {
    return null;
  }
  
  @PutMapping("/{idOrder}")
  public void update(@Valid @RequestBody ProductionOrderDTO order, @PathVariable("idOrder") Integer idOrder) {
    
  }
  
  @DeleteMapping("/{idOrder}")
  public void delete(@PathVariable("idOrder") Integer idOrder) {
    
  }
}
