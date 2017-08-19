package com.jmb.springfactory.controller.rol;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.jmb.springfactory.controller.ControllerExceptionAdvicer;
import com.jmb.springfactory.controller.api.RolController;
import com.jmb.springfactory.exceptions.NotFoundException;
import com.jmb.springfactory.model.dto.RolDto;
import com.jmb.springfactory.model.factory.RolDtoFactory;
import com.jmb.springfactory.model.factory.RolSamples;
import com.jmb.springfactory.service.rol.RolService;

@RunWith(MockitoJUnitRunner.class)
public class RolControllerTest {

  private MockMvc mockMvc;
  
  @InjectMocks
  private RolController controller;
  
  @Mock
  private RolService rolService;
  
  private final String notExistRolName = "rol_not_exist";
  private final String existRolName = RolSamples.NAME_ROL_TEST_1;
  private final String jsonRolFound = String.format("[{id:\"%s\",name:\"%s\"},{id:\"%s\",name:\"%s\"}"
      + ",{id:\"%s\",name:\"%s\"}]",
      RolSamples.ID_ROL_TEST_1, RolSamples.NAME_ROL_TEST_1, RolSamples.ID_ROL_TEST_2, 
      RolSamples.NAME_ROL_TEST_2, RolSamples.ID_ROL_TEST_3, RolSamples.NAME_ROL_TEST_3);
  
  private List<RolDto> listRolFound;

  @Before
  @SuppressWarnings("unchecked")
  public void setUp() throws NotFoundException {
    listRolFound = RolDtoFactory.createListSampleDefaultRoles();

    mockMvc = MockMvcBuilders.standaloneSetup(controller)
        .setControllerAdvice(new ControllerExceptionAdvicer()).build();

    when(rolService.findByNameContain(notExistRolName)).thenThrow(NotFoundException.class);
    when(rolService.findByNameContain(existRolName)).thenReturn(listRolFound);
  }
  
  @Test
  public void whenSearchRolByNameAndAnyRolContainItThenThrowNotFoundException() throws Exception {
    
    final RequestBuilder request = MockMvcRequestBuilders.get("/rol")
        .param("name", notExistRolName);
    
    mockMvc.perform(request)
      .andExpect(MockMvcResultMatchers.status().isNotFound());
  }
  
  @Test
  public void whenSearchRoleByNameAndExistSomeRolShouldReturnListOfJsonRoles() throws Exception {
    
    final RequestBuilder request = MockMvcRequestBuilders.get("/rol")
        .param("name", existRolName);
   
    final ResultActions result = mockMvc.perform(request);
    final String response = result.andReturn().getResponse().getContentAsString();
    
    result.andExpect(status().isOk());
    JSONAssert.assertEquals(jsonRolFound, response, false);
  }
  
  @Test
  public void whenSearchRoleByNameShouldInvokeFindByNameContainMethod() throws Exception {

    final RequestBuilder request = MockMvcRequestBuilders.get("/rol")
        .param("name", existRolName);

    mockMvc.perform(request);
    
    verify(rolService, times(1)).findByNameContain(existRolName);
    verifyNoMoreInteractions(rolService);
  }
}
