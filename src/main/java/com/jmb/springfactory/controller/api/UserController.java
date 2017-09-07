package com.jmb.springfactory.controller.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jmb.springfactory.controller.GenericController;
import com.jmb.springfactory.model.dto.UserDto;
import com.jmb.springfactory.service.GenericService;
import com.jmb.springfactory.service.user.UserService;

@RestController
@RequestMapping("/user")
public class UserController extends GenericController<UserDto, String>{

  @Autowired
  private UserService userService;
  
  public List<UserDto> findAll(@RequestParam(value = "name", required = false) String name,
      @RequestParam(value = "nif", required = false) String nif) {
    
    return null;
  }
  
  @Override
  public GenericService<UserDto, String> genericService() {
    return userService;
  }

}
