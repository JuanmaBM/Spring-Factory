package com.jmb.springfactory.dao.group;

import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jmb.springfactory.dao.GenericMongoServiceImpl;
import com.jmb.springfactory.model.entity.WorkGroup;
import com.jmb.springfactory.service.UtilsService;

@Repository
public class GroupMongoServiceImpl extends GenericMongoServiceImpl<WorkGroup, Integer> implements GroupMongoService {

  @Autowired
  private GroupRepository groupRepository;

  @Override
  public JpaRepository<WorkGroup, Integer> getRepository() {
    return groupRepository;
  }
  
  @Override
  public Stream<WorkGroup> findByName(String name) {
    
    final WorkGroup groupWithName = new WorkGroup();
    groupWithName.setName(name);
    
    final Example<WorkGroup>  groupByNameExample = Example.of(groupWithName, UtilsService.createMatcherExact("name"));
    
    return groupRepository.findAll(groupByNameExample).stream();
  }

}
