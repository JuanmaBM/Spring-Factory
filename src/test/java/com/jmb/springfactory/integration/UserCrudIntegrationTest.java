package com.jmb.springfactory.integration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import javax.validation.ValidationException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import com.jmb.springfactory.SpringFactoryApplication;
import com.jmb.springfactory.SpringFactoryTestConfiguration;
import com.jmb.springfactory.controller.api.UserController;
import com.jmb.springfactory.exceptions.NotFoundException;
import com.jmb.springfactory.exceptions.ServiceLayerException;
import com.jmb.springfactory.model.dto.UserDto;
import com.jmb.springfactory.model.entity.User;
import com.jmb.springfactory.model.factory.user.UserDtoFactory;
import com.jmb.springfactory.model.factory.user.UserFactory;
import com.jmb.springfactory.model.factory.user.UserSamples;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {SpringFactoryApplication.class, SpringFactoryTestConfiguration.class})
public class UserCrudIntegrationTest {

  @Autowired
  private UserController userController;
  
  @Autowired
  private MongoTemplate mongoTemplate;
  
  private final User newUser = UserFactory.createSampleDefaultUser(); 

  @Before
  public void beforeTest() {
    mongoTemplate.dropCollection(User.class);
  }
  
  @Test(expected = ValidationException.class)
  public void ifTheUserAlreadyExistThenShouldThrowPersistenceException() throws ServiceLayerException {

    final UserDto newUserDto = UserDtoFactory.createSampleDefaultUserDto(); 

    userController.create(newUserDto);
    userController.create(newUserDto);
  }
  
  @Test(expected = ValidationException.class)
  public void ifUserDtoHaveAnyEmptyFieldThenShouldThrowValidationException() throws ServiceLayerException  {
    
    final UserDto newUserWithEmptyField = new UserDto();
    
    userController.create(newUserWithEmptyField);
  }
  
  @Test(expected = NotFoundException.class)
  public void whenSearchUserByNifAndNotExistAnyOneThenShouldThrowNotFoundException() throws NotFoundException {
    
    final String nif = "11111111Q";

    userController.findAll(nif, null);
  }
  
  @Test(expected = NotFoundException.class)
  public void whenSearchUserByNameContainAndNotExistAnyOneThenShouldThrowNotFoundException() throws NotFoundException {
    
    final String name = "some name";
    
    userController.findAll(null, name);
  }
  
  @Test
  public void whenSearchUserByNifAndExistOneThenShouldReturnAListWithAtLeastOneResult() throws NotFoundException  {
    
    mongoTemplate.save(newUser);
    final String nif = UserSamples.NIF_USER_TEST_1;
    
    List<UserDto> usersWithNif = userController.findAll(nif, null);
    
    assertThat(usersWithNif)
      .isNotNull()
      .isNotEmpty();
    assert(usersWithNif.stream().anyMatch(user -> user.getNif().equals(nif)));
  }
  
  @Test 
  public void whenSearchUserNyNameContainAndExistOneThenShouldReturnAlistWithAtLeastOneResult() throws NotFoundException  {
    
    mongoTemplate.save(newUser);
    final String name = UserSamples.NAME_USER_TEST_1;
    
    List<UserDto> usersWithName = userController.findAll(null, name);
    
    assertThat(usersWithName)
      .isNotNull()
      .isNotEmpty();
    assert(usersWithName.stream().anyMatch(user -> user.getName().equals(name)));
  }
  
  @Test
  public void whenSearchUsersWithoutParameterShouldReturnUserDtoList() throws NotFoundException  {
    
    List<UserDto> allUsers = userController.findAll(null, null);
    
    assertNotNull(allUsers);
  }
}
