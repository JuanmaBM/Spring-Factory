package com.jmb.springfactory.service.group;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jmb.springfactory.dao.GenericMongoService;
import com.jmb.springfactory.dao.group.GroupMongoService;
import com.jmb.springfactory.model.bo.BusinessObjectBase;
import com.jmb.springfactory.model.dto.GroupDto;
import com.jmb.springfactory.model.entity.Group;
import com.jmb.springfactory.service.GenericServiceImpl;

@Service
public class GroupServiceImpl extends GenericServiceImpl<Group, GroupDto, BusinessObjectBase, String> implements 
  GroupService {
  
  @Autowired
  private GroupMongoService groupMongoService;

  @Override
  public GenericMongoService<Group, String> genericDao() {
    return groupMongoService;
  }

  @Override
  public Class<? extends Group> getClazz() {
    return Group.class;
  }

  @Override
  public Class<? extends GroupDto> getDtoClazz() {
    return GroupDto.class;
  }

  @Override
  public Class<? extends BusinessObjectBase> getBoClazz() {
    return BusinessObjectBase.class;
  }

}
