package com.jmb.springfactory.unit.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.jmb.springfactory.controller.ControllerExceptionAdvicer;
import com.jmb.springfactory.controller.api.UserController;
import com.jmb.springfactory.exceptions.NotFoundException;
import com.jmb.springfactory.model.dto.UserDto;
import com.jmb.springfactory.model.factory.user.UserDtoFactory;
import com.jmb.springfactory.service.user.UserService;

@RunWith(MockitoJUnitRunner.class)
public class UserControllerTest {

  private MockMvc mockMvc;
  
  private final String nameToSearch = "Juan";
  private final String nifToSearch = "12345678Z";
  private final String emptyUser = "{\"name\":\"Juan\"}";
  private final List<UserDto> usersFound = UserDtoFactory.createSampleDefaultListUser();
  
  @InjectMocks
  private UserController userController;
  
  @Mock
  private UserService userService;
  
  @Before
  public void beforeTest() {
    
    mockMvc = MockMvcBuilders.standaloneSetup(userController).setControllerAdvice(new ControllerExceptionAdvicer())
        .build();
  }
  
  @Test
  public void whenFindByNifThenUserServiceShouldInvokeFindByNifMethod() throws Exception {
    
    mockMvc.perform(MockMvcRequestBuilders.get("/user").param("nif", nifToSearch));
    
    verify(userService).findByNifContain(nifToSearch);
  }
  
  @Test
  public void whenSearchByNameContainThenUserServiceShouldInvokeFindByNameMethod() throws Exception  {
    
    mockMvc.perform(MockMvcRequestBuilders.get("/user").param("name", nameToSearch));
    
    verify(userService).findByNameContain(nameToSearch);
  }
  
  @Test 
  @SuppressWarnings("unchecked")
  public void whenSearchByNifAndNotExistAnyOneThenShouldReturnNotFoundException() throws Exception  {
    
    when(userService.findByNifContain(nifToSearch)).thenThrow(NotFoundException.class);
    mockMvc.perform(MockMvcRequestBuilders.get("/user").param("nif", nifToSearch))
      .andExpect(MockMvcResultMatchers.status().isNotFound());
  }
  
  @Test
  @SuppressWarnings("unchecked")
  public void whenSearchByNameAndNotExistAnyOneThenShouldReturnNotFoundException() throws Exception  {
    
    when(userService.findByNameContain(nameToSearch)).thenThrow(NotFoundException.class);
    mockMvc.perform(MockMvcRequestBuilders.get("/user").param("name", nameToSearch))
      .andExpect(MockMvcResultMatchers.status().isNotFound());
  }
  
  @Test
  public void whenCreateUserWithAnyFieldEmptyThenShouldThrowValidationException() throws Exception  {
    
    mockMvc.perform(MockMvcRequestBuilders.post("/user").content(emptyUser))
      .andExpect(MockMvcResultMatchers.status().isUnsupportedMediaType());
  }
  
  @Test
  public void whenSearchByNifAndExistOneThenShouldReturnAListWithTheUserFound() throws Exception  {
    
    when(userService.findByNifContain(nifToSearch)).thenReturn(usersFound);
    mockMvc.perform(MockMvcRequestBuilders.get("/user").param("nif", nifToSearch))
      .andExpect(MockMvcResultMatchers.status().isOk())
      .andExpect(MockMvcResultMatchers.jsonPath("$[0].nif", is(nifToSearch)));
  }
  
  @Test 
  public void whenSearchByNameAndExistOneThenShouldReturnListWithTheUserFound() throws Exception  {
    
    when(userService.findByNameContain(nameToSearch)).thenReturn(usersFound);
    mockMvc.perform(MockMvcRequestBuilders.get("/user").param("name", nameToSearch))
      .andExpect(MockMvcResultMatchers.status().isOk())
      .andExpect(MockMvcResultMatchers.jsonPath("$[0].name", is(nameToSearch)));
  }
  
}
