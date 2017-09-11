package com.jmb.springfactory.controller.api;

import java.util.List;

import static org.apache.commons.lang3.StringUtils.isNotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jmb.springfactory.controller.GenericController;
import com.jmb.springfactory.exceptions.NotFoundException;
import com.jmb.springfactory.model.dto.UserDto;
import com.jmb.springfactory.service.GenericService;
import com.jmb.springfactory.service.user.UserService;

@RestController
@RequestMapping("/user")
public class UserController extends GenericController<UserDto, String>{

  @Autowired
  private UserService userService;
  
  @GetMapping
  public List<UserDto> findAll(@RequestParam(value = "nif", required = false) String nif,
      @RequestParam(value = "name", required = false) String name) throws NotFoundException {
    
    if (isNotBlank(nif)) {
      return userService.findByNifContain(nif);

    } else if (isNotBlank(name)) {
      return userService.findByNameContain(name);
    }

    return userService.findAll();
  }
  
  @Override
  public GenericService<UserDto, String> genericService() {
    return userService;
  }

}
