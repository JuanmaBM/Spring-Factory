package com.jmb.springfactory.service.group;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.jmb.springfactory.dao.GenericMongoService;
import com.jmb.springfactory.dao.group.GroupMongoService;
import com.jmb.springfactory.exceptions.ServiceLayerException;
import com.jmb.springfactory.model.bo.BusinessObjectBase;
import com.jmb.springfactory.model.dto.GroupDto;
import com.jmb.springfactory.model.entity.Group;
import com.jmb.springfactory.service.GenericServiceImpl;
import com.jmb.springfactory.service.ValidatorService;

@Service
public class GroupServiceImpl extends GenericServiceImpl<Group, GroupDto, BusinessObjectBase, String> implements 
  GroupService {
  
  @Autowired
  private GroupMongoService groupMongoService;
  
  @Autowired
  @Qualifier("groupValidatorService")
  private ValidatorService groupValidatorService;

  @Override
  public GenericMongoService<Group, String> genericDao() {
    return groupMongoService;
  }
  
  @Override 
  public GroupDto save(GroupDto group) throws ServiceLayerException {
    groupValidatorService.validate(group);
    return super.save(group);
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
