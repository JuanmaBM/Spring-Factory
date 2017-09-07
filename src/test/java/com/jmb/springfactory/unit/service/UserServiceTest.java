package com.jmb.springfactory.unit.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyList;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.stream.Stream;

import javax.persistence.PersistenceException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;

import com.jmb.springfactory.dao.user.UserMongoService;
import com.jmb.springfactory.exceptions.NotFoundException;
import com.jmb.springfactory.exceptions.PersistenceLayerException;
import com.jmb.springfactory.exceptions.ServiceLayerException;
import com.jmb.springfactory.model.dto.UserDto;
import com.jmb.springfactory.model.entity.User;
import com.jmb.springfactory.model.factory.user.UserDtoFactory;
import com.jmb.springfactory.model.factory.user.UserFactory;
import com.jmb.springfactory.service.user.UserServiceImpl;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

  private static final String NAME_NOT_EXIST = "name not exist";
  private static final String NIF_NOT_EXISTS = "nif not exists";
  private static final String SOME_NAME = "Some name";
  private static final String SOME_NIF = "some nif";

  @InjectMocks
  private UserServiceImpl userService;
  
  @Mock
  private UserMongoService userMongoService;
  
  @Mock
  private ModelMapper modelMapper;
  
  private final Stream<User> emptyUsersStream = Stream.empty();
  private final Stream<User> usersFoundStream = UserFactory.createSampleDefaultStreamUser();
  private final UserDto userToSearch = UserDtoFactory.createSampleDefaultUserDto();
  
  @Before
  public void beforeTest() {
    when(modelMapper.map(any(User.class), eq(UserDto.class))).thenReturn(any(UserDto.class));
  }
  
  @Test
  @SuppressWarnings("unchecked")
  public void whenSearchByNifThenShouldInvokefindByNifContainMethod() {
    
    userService.findByNifContain(SOME_NIF);
    verify(userMongoService, times(1)).findByNifContain(SOME_NIF);
    verify(userService, times(1)).convertListEntityToListDto(anyList());
  }
  
  @Test
  @SuppressWarnings("unchecked")
  public void whenSearchByNameThenShouldInvokeFindByNameContainMethod() {
    
    userService.findByNameContain(SOME_NAME);
    verify(userMongoService, times(1)).findByNameContain(SOME_NAME);
    verify(userService, times(1)).convertListEntityToListDto(anyList());
  }
  
  @Test(expected = NotFoundException.class)
  public void whenSearchByNifAndNotExistAnyOneThenShouldReturnNotFoundException() {
    
    when(userMongoService.findByNifContain(any(String.class))).thenReturn(emptyUsersStream);
    userService.findByNifContain(NIF_NOT_EXISTS);
  }

  @Test(expected = NotFoundException.class)
  public void whenSearchByNameAndNotExistAnyOneThenShouldReturnNotFoundException() {
    
    when(userMongoService.findByNameContain(any(String.class))).thenReturn(emptyUsersStream);
    userService.findByNameContain(NAME_NOT_EXIST);
  }
  
  @Test(expected = ServiceLayerException.class)
  @SuppressWarnings("unchecked")
  public void whenCreateUserThatHaveAnyIncorrectFieldThenShouldReturnPersistenceException() 
      throws ServiceLayerException, PersistenceLayerException {
    
    final UserDto newUserDto = UserDto.builder().build();
    final User newUser = User.builder().build();
    
    when(userMongoService.save(newUser)).thenThrow(PersistenceException.class);
    userService.save(newUserDto);
  }
  
  @Test
  public void whenSearchByNameAndExistAnyOneThenReturnListWithResults() {
    
    final String nameToSearch = userToSearch.getName();
    
    when(userMongoService.findByNameContain(nameToSearch)).thenReturn(usersFoundStream);
    final List<UserDto> usersDtoFound = userService.findByNameContain(nameToSearch);
    
    assertThat(usersDtoFound).isNotNull().isNotEmpty();
    assertTrue(usersDtoFound.stream().anyMatch(user -> user.getName().equals(nameToSearch)));
  }
  
  @Test
  public void whenSearchByNifAndExistSomeOneThenReturnListWithResults() {
    
    final String nifToSearch = userToSearch.getNif();
    
    when(userMongoService.findByNifContain(nifToSearch)).thenReturn(usersFoundStream);
    final List<UserDto> usersDtoFound = userService.findByNifContain(nifToSearch);
    
    assertThat(usersDtoFound).isNotNull().isNotEmpty();
    assertTrue(usersDtoFound.stream().anyMatch(user -> user.getName().equals(nifToSearch)));
  }
}
