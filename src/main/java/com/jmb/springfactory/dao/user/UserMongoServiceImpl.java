package com.jmb.springfactory.dao.user;

import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.jmb.springfactory.dao.GenericMongoServiceImpl;
import com.jmb.springfactory.model.entity.User;

@Repository
public class UserMongoServiceImpl extends GenericMongoServiceImpl<User, String> implements UserMongoService {
  
  @Autowired
  private UserRepository userRepository;

  @Override
  public Stream<User> findByNameContain(String name) {
    return null;
  }

  @Override
  public Stream<User> findByNifContain(String nif) {
    return null;
  }

  @Override
  public MongoRepository<User, String> getRepository() {
    return userRepository;
  }


}
