package com.jmb.springfactory.dao.group;

import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.jmb.springfactory.dao.GenericMongoServiceImpl;
import com.jmb.springfactory.model.entity.Group;
import com.jmb.springfactory.service.UtilsService;

@Repository
public class GroupMongoServiceImpl extends GenericMongoServiceImpl<Group, String> implements GroupMongoService {

  @Autowired
  private GroupRepository groupRepository;

  @Override
  public MongoRepository<Group, String> getRepository() {
    return groupRepository;
  }
  
  @Override
  public Stream<Group> findByName(String name) {
    
    final Group groupWithName = new Group();
    groupWithName.setName(name);
    
    final Example<Group>  groupByNameExample = Example.of(groupWithName, UtilsService.createMatcherExact("name"));
    
    return groupRepository.findAll(groupByNameExample).stream();
  }

}
