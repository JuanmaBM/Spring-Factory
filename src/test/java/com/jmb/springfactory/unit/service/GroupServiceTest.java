package com.jmb.springfactory.unit.service;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import javax.validation.ValidationException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;

import com.jmb.springfactory.dao.group.GroupMongoService;
import com.jmb.springfactory.exceptions.PersistenceLayerException;
import com.jmb.springfactory.exceptions.ServiceLayerException;
import com.jmb.springfactory.model.dto.WorkGroupDto;
import com.jmb.springfactory.model.entity.WorkGroup;
import com.jmb.springfactory.model.factory.group.GroupDtoFactory;
import com.jmb.springfactory.model.factory.group.GroupFactory;
import com.jmb.springfactory.service.group.GroupServiceImpl;
import com.jmb.springfactory.service.group.GroupValidatorService;

@RunWith(MockitoJUnitRunner.class)
public class GroupServiceTest {

  @InjectMocks
  private GroupServiceImpl groupService;
  
  @Mock
  private GroupValidatorService groupValidatorService;
  
  @Mock
  private GroupMongoService groupMongoService;
  
  @Mock
  private ModelMapper modelMapper;
  
  private WorkGroupDto newGroupDto = GroupDtoFactory.createSampleDefaultGroupDto();
  private WorkGroup newGroup = GroupFactory.createSampleDefaultGroup();
  
  @Before
  public void beforeTest() {
    when(modelMapper.map(any(WorkGroup.class), eq(WorkGroupDto.class))).thenReturn(newGroupDto);
    when(modelMapper.map(any(WorkGroupDto.class), eq(WorkGroup.class))).thenReturn(newGroup);
  }
  
  @Test
  public void whenCreateGroupShouldInvokeValidateAndThenSaveMethod() throws ServiceLayerException,
    PersistenceLayerException {
    
    doNothing().when(groupValidatorService).validate(newGroupDto);
    when(groupMongoService.save(newGroup)).thenReturn(newGroup);
    
    groupService.save(newGroupDto);
    
    verify(groupValidatorService).validate(newGroupDto);
    verify(groupMongoService).save(newGroup);
  }
  
  @Test(expected = ValidationException.class)
  public void whenCreateGroupAndAlreadyExistOneThenThrowValidationException() throws ServiceLayerException,
    PersistenceLayerException {
    
    doThrow(ValidationException.class).when(groupValidatorService).validate(newGroupDto);
    
    groupService.save(newGroupDto);
    
    verify(groupMongoService, times(0)).save(newGroup);
  }
}
