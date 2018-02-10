package com.jmb.springfactory.dao.user;

import java.util.stream.Stream;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jmb.springfactory.model.entity.Permission;
import com.jmb.springfactory.model.entity.User;

public interface UserRepository extends JpaRepository<User, Integer>{

  public Stream<User> findByRol_PermissionsIn(final Permission permission);
}
