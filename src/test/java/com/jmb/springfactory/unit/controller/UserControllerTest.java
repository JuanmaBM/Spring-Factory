package com.jmb.springfactory.unit.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.times;
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
import com.jmb.springfactory.exceptions.NotFoundException;
import com.jmb.springfactory.model.dto.UserDto;
import com.jmb.springfactory.model.factory.user.UserDtoFactory;

@RunWith(MockitoJUnitRunner.class)
public class UserControllerTest {

  private MockMvc mockMvc;
  
  private final String nameToSearch = "Juan";
  private final String nifToSearch = "11111111R";
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
  public void whenFindByNifThenUserServiceShouldInvokeFindByNifMethod() {
    
    mockMvc.perform(MockMvcRequestBuilders.get("/user").param("nif", nifToSearch));
    
    verify(userController, times(1)).findAll(nifToSearch, null);
    verify(userService, times(1)).findByNifContain(nifToSearch);
  }
  
  @Test
  public void whenSearchByNameContainThenUserServiceShouldInvokeFindByNameMethod() {
    
    mockMvc.perform(MockMvcRequestBuilders.get("/user").param("name", nameToSearch));
    
    verify(userController, times(1)).findAll(null, nameToSearch);
    verify(userService, times(1)).findByNameContain(nameToSearch);
  }
  
  @Test 
  public void whenSearchByNifAndNotExistAnyOneThenShouldReturnNotFoundException() {
    
    when(userService.findByNifContain(nifToSearch)).thenThrow(NotFoundException.class);
    mockMvc.perform(MockMvcRequestBuilders.get("/user").param("nif", nifToSearch))
      .andExpect(MockMvcResultMatchers.status().isNotFound());
  }
  
  @Test
  public void whenSearchByNameAndNotExistAnyOneThenShouldReturnNotFoundException() {
    
    when(userService.findByNameContain(nameToSearch)).thenThrow(NotFoundException.class);
    mockMvc.perform(MockMvcRequestBuilders.get("/user").param("nif", nameToSearch))
      .andExpect(MockMvcResultMatchers.status().isNotFound());
  }
  
  @Test
  public void whenCreateUserWithAnyFieldEmptyThenShouldThrowValidationException() {
    
    mockMvc.perform(MockMvcRequestBuilders.post("/user").content(emptyUser))
      .andExpect(MockMvcResultMatchers.status().isUnprocessableEntity());
  }
  
  @Test
  public void whenSearchByNifAndExistOneThenShouldReturnAListWithTheUserFound() {
    
    when(userService.findByNifContain(nifToSearch)).thenReturn(usersFound);
    mockMvc.perform(MockMvcRequestBuilders.get("/user").param("nif", nifToSearch))
      .andExpect(MockMvcResultMatchers.status().isOk())
      .andExpect(MockMvcResultMatchers.jsonPath("$[0].nif", is(nifToSearch)));
  }
  
  @Test 
  public void whenSearchByNameAndExistOneThenShouldReturnListWithTheUserFound() {
    
    when(userService.findByNameContain(nameToSearch)).thenReturn(usersFound);
    mockMvc.perform(MockMvcRequestBuilders.get("/user").param("name", nameToSearch))
      .andExpect(MockMvcResultMatchers.status().isOk())
      .andExpect(MockMvcResultMatchers.jsonPath("$[0].name", is(nameToSearch)));
  }
  
}
