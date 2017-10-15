package com.jmb.springfactory.service.group;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.jmb.springfactory.dao.GenericMongoService;
import com.jmb.springfactory.dao.group.GroupMongoService;
import com.jmb.springfactory.exceptions.ServiceLayerException;
import com.jmb.springfactory.model.bo.BusinessObjectBase;
import com.jmb.springfactory.model.dto.WorkGroupDto;
import com.jmb.springfactory.model.entity.WorkGroup;
import com.jmb.springfactory.service.GenericServiceImpl;
import com.jmb.springfactory.service.ValidatorService;

@Service
public class GroupServiceImpl extends GenericServiceImpl<WorkGroup, WorkGroupDto, BusinessObjectBase, Integer> implements 
  GroupService {
  
  @Autowired
  private GroupMongoService groupMongoService;
  
  @Autowired
  @Qualifier("groupValidatorService")
  private ValidatorService groupValidatorService;

  @Override
  public GenericMongoService<WorkGroup, Integer> genericDao() {
    return groupMongoService;
  }
  
  @Override 
  public WorkGroupDto save(WorkGroupDto group) throws ServiceLayerException {
    groupValidatorService.validate(group);
    return super.save(group);
  }

  @Override
  public Class<? extends WorkGroup> getClazz() {
    return WorkGroup.class;
  }

  @Override
  public Class<? extends WorkGroupDto> getDtoClazz() {
    return WorkGroupDto.class;
  }

  @Override
  public Class<? extends BusinessObjectBase> getBoClazz() {
    return BusinessObjectBase.class;
  }

}
