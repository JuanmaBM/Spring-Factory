package com.jmb.springfactory.integration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.notNull;

import java.util.List;

import javax.persistence.PersistenceException;
import javax.validation.ValidationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.internal.matchers.GreaterThan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.jmb.springfactory.SpringFactoryApplication;
import com.jmb.springfactory.exceptions.NotFoundException;
import com.jmb.springfactory.model.dto.UserDto;
import com.jmb.springfactory.model.factory.user.UserDtoFactory;
import com.jmb.springfactory.model.factory.user.UserSamples;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringFactoryApplication.class)
@EnableAutoConfiguration
public class UserCrudIntegrationTest {

  @Autowired
  private UserController userController;
  
  @Test(expected = PersistenceException.class)
  public void ifTheUserAlreadyExistThenShouldThrowPersistenceException() {

    final UserDto newUser = UserDtoFactory.createSampleDefaultUserDto();

    userController.create(newUser);
    userController.create(newUser);
  }
  
  @Test(expected = ValidationException.class)
  public void ifUserDtoHaveAnyEmptyFieldThenShouldThrowValidationException() {
    
    final UserDto newUserWithEmptyField = UserDto.builder().build();
    
    userController.create(newUserWithEmptyField);
  }
  
  @Test(expected = NotFoundException.class)
  public void whenSearchUserByNifAndNotExistAnyOneThenShouldThrowNotFoundException() {
    
    final String nif = "11111111Q";

    userController.findAll(nif, null);
  }
  
  @Test(expected = NotFoundException.class)
  public void whenSearchUserByNameContainAndNotExistAnyOneThenShouldThrowNotFoundException() {
    
    final String name = "some name";
    
    userController.findAll(null, name);
  }
  
  @Test
  public void whenSearchUserByNifAndExistOneThenShouldReturnAListWithAtLeastOneResult() {
    
    final String nif = UserSamples.NIF_USER_TEST_1;
    
    List<UserDto> usersWithNif = userController.findAll(nif, null);
    
    assertThat(usersWithNif)
      .isNotNull()
      .isNotEmpty();
    assert(usersWithNif.stream().anyMatch(user -> user.getNif().equals(nif)));
  }
  
  @Test 
  public void whenSearchUserNyNameContainAndExistOneThenShouldReturnAlistWithAtLeastOneResult() {
    
    final String name = UserSamples.NAME_USER_TEST_1;
    
    List<UserDto> usersWithName = userController.findAll(null, name);
    
    assertThat(usersWithName)
      .isNotNull()
      .isNotEmpty();
    assert(usersWithName.stream().anyMatch(user -> user.getName().equals(name)));
  }
  
  @Test
  public void whenSearchUsersWithoutParameterShouldReturnUserDtoList() {
    
    List<UserDto> allUsers = userController.findAll(null, null);
    
    assertNotNull(allUsers);
  }
}