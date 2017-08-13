package com.jmb.springfactory.controller.api;

import org.springframework.beans.factory.annotation.Autowired;

import com.jmb.springfactory.controller.GenericController;
import com.jmb.springfactory.model.dto.RolDto;
import com.jmb.springfactory.service.GenericService;
import com.jmb.springfactory.service.rol.RolService;

public class RolController extends GenericController<RolDto, String>{

  @Autowired
  private RolService rolService;
  
  @Override
  public GenericService<RolDto, String> genericService() {
    return rolService;
  }

}
