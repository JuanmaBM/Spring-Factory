package integration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.jmb.springfactory.SpringFactoryApplication;
import com.jmb.springfactory.controller.api.RolController;
import com.jmb.springfactory.exceptions.NotFoundException;
import com.jmb.springfactory.model.dto.RolDto;
import com.jmb.springfactory.model.factory.RolDtoFactory;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringFactoryApplication.class)
@EnableAutoConfiguration
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
    
    assertNotNull(response);
    assertThat(!response.isEmpty());
    assert(response.stream().anyMatch(rol -> rol.getId().equals(newRol.getId())));
  }
}
