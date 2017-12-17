package com.jmb.springfactory.dao.user;

import java.util.Optional;
import java.util.stream.Stream;

import com.jmb.springfactory.dao.GenericMySQLService;
import com.jmb.springfactory.model.entity.User;

public interface UserMongoService extends GenericMySQLService<User, Integer>{

  public Stream<User> findByNameContain(String name);
  
  public Stream<User> findByNifContain(String nif);

  public Optional<User> findByNif(String nif);
}
