package com.jmb.springfactory.controller.api;

import java.util.List;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jmb.springfactory.model.dto.ProductionOrderDTO;

@RestController
@RequestMapping("/schedule/{idSchedule}/order")
public class ProducionOrderController {

  @GetMapping
  public List<ProductionOrderDTO> findAll(@PathVariable("idSchedule") Integer idSchedule) {
    return null;
  }
  
  @GetMapping("/{idOrder}")
  public List<ProductionOrderDTO> findById(@PathVariable("idSchedule") Integer idSchedule, 
      @PathVariable("idOrder") Integer idOrder) {
    return null;
  }
  
  @PostMapping
  public ProductionOrderDTO create(@Valid @RequestBody ProductionOrderDTO order) {
    return null;
  }
  
  @PutMapping("/{idOrder}")
  public void update(@Valid @RequestBody ProductionOrderDTO order, @PathVariable("idOrder") Integer idOrder) {
    
  }
  
  @DeleteMapping("/{idOrder}")
  public void delete(@PathVariable("idOrder") Integer idOrder) {
    
  }
}
