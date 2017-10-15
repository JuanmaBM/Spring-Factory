package com.jmb.springfactory.dao.user;

import java.util.stream.Stream;

import com.jmb.springfactory.dao.GenericMongoService;
import com.jmb.springfactory.model.entity.User;

public interface UserMongoService extends GenericMongoService<User, Integer>{

  public Stream<User> findByNameContain(String name);
  
  public Stream<User> findByNifContain(String nif);
}
