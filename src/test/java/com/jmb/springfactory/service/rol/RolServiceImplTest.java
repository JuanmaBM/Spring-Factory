package com.jmb.springfactory.service.rol;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.stream.Stream;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.jmb.springfactory.dao.rol.RolMongoService;
import com.jmb.springfactory.exceptions.NotFoundException;
import com.jmb.springfactory.model.bo.BusinessObjectBase;
import com.jmb.springfactory.model.dto.RolDto;
import com.jmb.springfactory.model.entity.Rol;
import com.jmb.springfactory.model.factory.RolDtoFactory;
import com.jmb.springfactory.model.factory.RolFactory;
import com.jmb.springfactory.model.factory.RolSamples;
import com.jmb.springfactory.service.GenericTransformerServiceImpl;
import com.jmb.springfactory.service.rol.RolServiceImpl;

@RunWith(MockitoJUnitRunner.class)
public class RolServiceImplTest {

  @InjectMocks
  private RolServiceImpl rolService;
  
  @Mock
  private RolMongoService rolMongoService;
  
  @Mock
  private GenericTransformerServiceImpl<Rol, RolDto, BusinessObjectBase> transformerService;
  
  private final static String SOME_ROL_NAME = "SOME ROL";

  private Stream<Rol> listRolesFounded;
  private Stream<RolDto> transformedRolesFound;

  @Before
  public void setUp() {
    listRolesFounded = RolFactory.createStreamSampleDefaultRoles();
    transformedRolesFound = RolDtoFactory.createStreamSampleDefaultRoles();
    
    when(rolMongoService.findByNameContain(RolSamples.NAME_ROL_TEST_1)).thenReturn(listRolesFounded);
    when(transformerService.convertStreamEntityToStreamDto(listRolesFounded)).thenReturn(transformedRolesFound);
  }
  
  @Test
  public void whenSearchRolByNameShouldInvokeRolMongoServiceMethod() throws NotFoundException {
    rolService.findByNameContain(RolSamples.NAME_ROL_TEST_1);
    verify(rolMongoService).findByNameContain(RolSamples.NAME_ROL_TEST_1);
  }
  
  @Test
  public void whenSearchRolByNameContainShouldInvokeGenericTransformerMethod() throws NotFoundException {
    rolService.findByNameContain(RolSamples.NAME_ROL_TEST_1);
    verify(transformerService).convertStreamEntityToStreamDto(listRolesFounded);
  }

  @Test(expected = NotFoundException.class)
  public void whenSearchRolByNameShouldThrowAExceptionIfRolNotExists() throws NotFoundException {
    rolService.findByNameContain(SOME_ROL_NAME);
  }
  
  @Test
  public void whenAnyRolIsFoundedThenReturnListOfRolDto() throws NotFoundException {
    List<RolDto> rolesFounded = rolService.findByNameContain(RolSamples.NAME_ROL_TEST_1);
    
    assertNotNull(rolesFounded);
    assertEquals(rolesFounded.size(), listRolesFounded.count());
  }
}
