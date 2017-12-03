package com.jmb.springfactory.unit.controller;


import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;

import javax.validation.ValidationException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jmb.springfactory.controller.ControllerExceptionAdvicer;
import com.jmb.springfactory.controller.api.GroupController;
import com.jmb.springfactory.exceptions.ServiceLayerException;
import com.jmb.springfactory.model.dto.WorkGroupDto;
import com.jmb.springfactory.model.factory.group.GroupDtoFactory;
import com.jmb.springfactory.service.group.GroupService;

@RunWith(MockitoJUnitRunner.class)
public class GroupControllerTest {

  private MockMvc mockMvc;
  
  @InjectMocks
  private GroupController groupController;
  
  @Mock
  private GroupService groupService;
  
  private ObjectMapper mapper = new ObjectMapper();
  
  @Before
  public void beforeTest() {
    
    mockMvc = MockMvcBuilders.standaloneSetup(groupController)
        .setControllerAdvice(new ControllerExceptionAdvicer()).build();
  }
  
  @Test
  public void whenCreateGroupShouldInvokeSaveMethodFromGroupService()throws ServiceLayerException,
  Exception {
    
    final WorkGroupDto newGroupDto = GroupDtoFactory.createSampleDefaultGroupDto();

    mockMvc.perform(MockMvcRequestBuilders.post("/group")
        .content(mapper.writeValueAsString(newGroupDto))
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON));
    
    verify(groupService).save(newGroupDto);
  }
  
  @Test
  @SuppressWarnings("unchecked")
  public void whenCreateGroupThatHaveAnyEmptyFieldThenThrowValidationException() throws ServiceLayerException,
    Exception {

    final WorkGroupDto newGroupDto = GroupDtoFactory.createGroup(null, null, null, null);
    
    when(groupService.save(newGroupDto)).thenThrow(ValidationException.class);

    mockMvc.perform(MockMvcRequestBuilders.post("/group")
        .content(mapper.writeValueAsString(newGroupDto))
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest());
  }

  @Test
  public void whenCallGetWithoutParamsInvokeFindAllMethod() throws ServiceLayerException,
  Exception {
    
    when(groupService.findAll()).thenReturn(new ArrayList<>());

    mockMvc.perform(MockMvcRequestBuilders.get("/group"));

    verify(groupService).findAll();
  }
}
