package com.jmb.springfactory.integration;

import static org.junit.Assert.assertNotNull;

import javax.validation.ValidationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.jmb.springfactory.SpringFactoryApplication;
import com.jmb.springfactory.SpringFactoryTestConfiguration;
import com.jmb.springfactory.controller.api.GroupController;
import com.jmb.springfactory.exceptions.ServiceLayerException;
import com.jmb.springfactory.model.dto.GroupDto;
import com.jmb.springfactory.model.factory.group.GroupDtoFactory;
import static com.jmb.springfactory.model.factory.group.GroupSamples.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {SpringFactoryApplication.class, SpringFactoryTestConfiguration.class})
public class GroupIntegrationTest {

  @Autowired
  private GroupController groupController;
  
  @Test(expected = ValidationException.class)
  public void whenCreateGroupAndExistAnyOneWithTheSameNameThenThrowValidationException() throws ServiceLayerException {
    
    final GroupDto newGroupDto = GroupDtoFactory.createSampleDefaultGroupDto();
    
    groupController.create(newGroupDto);
    groupController.create(newGroupDto);
  }
  
  @Test(expected = ValidationException.class)
  public void whenCreateGroupThatHaveAnyEmptyFieldThenThrowValidationException() throws ServiceLayerException {
    
    final GroupDto newGroupDto = GroupDtoFactory.createGroup(null, null, null, null);
    
    groupController.create(newGroupDto);
  }
  
  @Test(expected = ValidationException.class)
  public void whenCreateGroupThatHaveIncorrectFomatHourFieldsThenThrowValidationException() 
      throws ServiceLayerException {
    
    final GroupDto newGroupDto = GroupDtoFactory.createGroup(null, null, "asddas", "1231:123123");
    
    groupController.create(newGroupDto);
  }

  @Test
  public void whenCreateGroupTheGroupIdMustNotBeNull() throws ServiceLayerException {
    
    final GroupDto newGroupDto = GroupDtoFactory.createGroup(null, NAME_GROUP_TEST_2, START_HOUR_GROUP_TEST_2,
        FINISH_HOUR_GROUP_TEST_2);
    
    final GroupDto groupCreated = groupController.create(newGroupDto);
    
    assertNotNull(groupCreated);
    assertNotNull(groupCreated.getId());
  }
  
}
