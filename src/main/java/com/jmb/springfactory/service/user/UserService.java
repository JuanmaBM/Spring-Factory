package com.jmb.springfactory.service.user;

import java.util.List;

import com.jmb.springfactory.model.dto.UserDto;
import com.jmb.springfactory.service.GenericService;

public interface UserService extends GenericService<UserDto, String> {

  public List<UserDto> findByNifContain(String nif);

  public List<UserDto> findByNameContain(String name);
}
