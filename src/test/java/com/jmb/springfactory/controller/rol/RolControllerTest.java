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
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.jmb.springfactory.controller.api.RolController;
import com.jmb.springfactory.exceptions.NotFoundException;
import com.jmb.springfactory.model.dto.RolDto;
import com.jmb.springfactory.model.factory.RolDtoFactory;
import com.jmb.springfactory.model.factory.RolSamples;
import com.jmb.springfactory.service.rol.RolService;

@RunWith(SpringRunner.class)
@WebMvcTest(value = RolController.class, secure = false)
public class RolControllerTest {

  @Autowired
  private MockMvc mockMvc;
  
  @MockBean
  private RolService rolService;
  
  private final String notExistRolName = "rol_not_exist";
  private final String existRolName = RolSamples.NAME_ROL_TEST_1;
  private final String jsonRolFound = String.format("[{id:%s,name:%s},{id:%s,name:%s},{id:%s,name:%s}]",
      RolSamples.NAME_ROL_TEST_1, RolSamples.NAME_ROL_TEST_1, RolSamples.NAME_ROL_TEST_2, 
      RolSamples.NAME_ROL_TEST_2, RolSamples.NAME_ROL_TEST_3, RolSamples.NAME_ROL_TEST_3);
  
  private List<RolDto> listRolFound;

  @Before
  @SuppressWarnings("unchecked")
  public void setUp() throws NotFoundException {
    listRolFound = RolDtoFactory.createListSampleDefaultRoles();

    when(rolService.findByNameContain(notExistRolName)).thenThrow(NotFoundException.class);
    when(rolService.findByNameContain(existRolName)).thenReturn(listRolFound);
  }
  
  @Test
  public void whenSearchRolByNameAndAnyRolContainItThenThrowNotFoundException() throws Exception {
    
    final RequestBuilder request = MockMvcRequestBuilders.get("/rol")
        .param("name", notExistRolName)
        .accept(MediaType.APPLICATION_JSON);
    
    mockMvc.perform(request).andExpect(status().isNotFound());
  }
  
  @Test
  public void whenSearchRoleByNameAndExistSomeRolShouldReturnListOfJsonRoles() throws Exception {
    
    final RequestBuilder request = MockMvcRequestBuilders.get("/rol")
        .param("name", existRolName)
        .accept(MediaType.APPLICATION_JSON);
   
    final ResultActions result = mockMvc.perform(request);
    final String response = result.andReturn().getResponse().getContentAsString();
    
    result.andExpect(status().isOk());
    JSONAssert.assertEquals(jsonRolFound, response, false);
  }
  
  @Test
  public void whenSearchRoleByNameShouldInvokeFindByNameContainMethod() throws Exception {

    final RequestBuilder request = MockMvcRequestBuilders.get("/rol")
        .param("name", existRolName)
        .accept(MediaType.APPLICATION_JSON);

    mockMvc.perform(request);
    
    verify(rolService, times(1)).findByNameContain(existRolName);
    verifyNoMoreInteractions(rolService);
  }
}
