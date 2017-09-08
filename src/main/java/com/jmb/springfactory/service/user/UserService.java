package com.jmb.springfactory.service.user;

import java.util.List;

import com.jmb.springfactory.exceptions.NotFoundException;
import com.jmb.springfactory.model.dto.UserDto;
import com.jmb.springfactory.service.GenericService;

public interface UserService extends GenericService<UserDto, String> {

  public List<UserDto> findByNifContain(String nif) throws NotFoundException;

  public List<UserDto> findByNameContain(String name) throws NotFoundException;
}
