package com.jmb.springfactory.dao.user;

import java.util.Optional;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.GenericPropertyMatcher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jmb.springfactory.dao.GenericMySQLServiceImpl;
import com.jmb.springfactory.model.entity.Permission;
import com.jmb.springfactory.model.entity.User;

@Repository
public class UserMongoServiceImpl extends GenericMySQLServiceImpl<User, Integer> implements UserMongoService {
  
  @Autowired
  private UserRepository userRepository;

  @Override
  public Stream<User> findByNameContain(String name) {

    final User userWithName = new User();
    userWithName.setName(name);

    final Example<User> userByNameExample = Example.of(userWithName, createMatcherContain("name"));

    return userRepository.findAll(userByNameExample).stream();
  }

  @Override
  public Stream<User> findByNifContain(String nif) {

    final User userWithNif = new User();
    userWithNif.setNif(nif);

    final Example<User> userByNifExample = Example.of(userWithNif, createMatcherContain("nif"));

    return userRepository.findAll(userByNifExample).stream();
  }
  
  @Override
  public Optional<User> findByNif(String nif) {

    final User userWithNif = new User();
    userWithNif.setNif(nif);
    
    final Example<User> userByNifExample = Example.of(userWithNif);
    
    return Optional.ofNullable(userRepository.findOne(userByNifExample));
  }
  
  @Override
  public Stream<User> findByPermission(final Permission permission) {
    return userRepository.findByRol_PermissionsIn(permission);
  }

  @Override
  public JpaRepository<User, Integer> getRepository() {
    return userRepository;
  }
  
  /**
   * Create a example matcher to search a entity that contains the value in field propertyName 
   * 
   * @param propertyName
   * @return
   */
  private ExampleMatcher createMatcherContain(String propertyName) {

    if (propertyName == null) {
      return null;
    }

    return ExampleMatcher.matching().withMatcher(propertyName, GenericPropertyMatcher::contains);
  }
}
