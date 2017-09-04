package com.jmb.springfactory.controller.api;

import java.util.List;

import static org.apache.commons.lang3.StringUtils.isNoneBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jmb.springfactory.controller.GenericController;
import com.jmb.springfactory.exceptions.NotFoundException;
import com.jmb.springfactory.model.dto.RolDto;
import com.jmb.springfactory.service.GenericService;
import com.jmb.springfactory.service.rol.RolService;

@RestController
@RequestMapping("/rol")
public class RolController extends GenericController<RolDto, String>{

  @Autowired
  private RolService rolService;
  
  @Override
  public GenericService<RolDto, String> genericService() {
    return rolService;
  }
  
  @GetMapping
  public List<RolDto> findAll(@RequestParam(name = "name", required = false) String rolName)
      throws NotFoundException {

    if (isNoneBlank(rolName)) {
      return rolService.findByNameContain(rolName);
    }
    
    return rolService.findAll();
  }
}
