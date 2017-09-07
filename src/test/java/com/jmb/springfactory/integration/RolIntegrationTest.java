package com.jmb.springfactory.integration;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.jmb.springfactory.SpringFactoryApplication;
import com.jmb.springfactory.controller.api.RolController;
import com.jmb.springfactory.exceptions.NotFoundException;
import com.jmb.springfactory.model.dto.RolDto;
import com.jmb.springfactory.model.factory.rol.RolDtoFactory;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringFactoryApplication.class)
@EnableAutoConfiguration(exclude = {SecurityAutoConfiguration.class,
  DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class, 
  HibernateJpaAutoConfiguration.class})
public class RolIntegrationTest {
  
  @Autowired
  private RolController rolController;

  @Before
  public void beforeTest() {
  }
  
  @Test(expected = NotFoundException.class)
  public void ifAnyRolContainSampleNameShouldReturnNotFoundException() throws Exception {
    rolController.findAll("Some name");
  }
  
  @Test
  public void whenSearchRoleByNameAndExistSomeRolShouldReturnListOfJsonRoles() throws Exception {

    RolDto newRol = RolDtoFactory.createSampleDefaultRol();
    rolController.create(newRol);
    
    List<RolDto> response = rolController.findAll(null);
    
    assertThat(response)
      .isNotNull()
      .isNotEmpty();
    assert(response.stream().anyMatch(rol -> rol.getId().equals(newRol.getId())));
  }
}
