package com.jmb.springfactory.dao.group;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.jmb.springfactory.dao.GenericMongoServiceImpl;
import com.jmb.springfactory.model.entity.Group;

@Repository
public class GroupMongoServiceImpl extends GenericMongoServiceImpl<Group, String> {

  @Autowired
  private GroupRepository groupRepository;

  @Override
  public MongoRepository<Group, String> getRepository() {
    return groupRepository;
  }

}
