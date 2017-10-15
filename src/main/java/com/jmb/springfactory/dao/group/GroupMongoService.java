package com.jmb.springfactory.dao.group;

import java.util.stream.Stream;

import com.jmb.springfactory.dao.GenericMongoService;
import com.jmb.springfactory.model.entity.WorkGroup;

public interface GroupMongoService extends GenericMongoService<WorkGroup, Integer> {

  public Stream<WorkGroup> findByName(String name);
}
