package com.jmb.springfactory.dao.group;

import java.util.stream.Stream;

import com.jmb.springfactory.dao.GenericMongoService;
import com.jmb.springfactory.model.entity.Group;

public interface GroupMongoService extends GenericMongoService<Group, String> {

  public Stream<Group> findByName(String name);
}
