package com.jmb.springfactory.service.user;

import java.util.List;
import java.util.Optional;

import com.jmb.springfactory.exceptions.NotFoundException;
import com.jmb.springfactory.model.dto.UserDto;
import com.jmb.springfactory.service.GenericService;

public interface UserService extends GenericService<UserDto, Integer> {

  public List<UserDto> findByNifContain(String nif) throws NotFoundException;

  public List<UserDto> findByNameContain(String name) throws NotFoundException;

  public Optional<UserDto> findByNif(String nif); 

  public List<UserDto> findIssueManagerUsers() throws NotFoundException;
}
