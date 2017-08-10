package com.jmb.springfactory.rol.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import com.jmb.springfactory.dao.rol.RolDaoImpl;
import com.jmb.springfactory.dao.rol.RolRepository;
import com.jmb.springfactory.exceptions.PersistenceLayerException;
import com.jmb.springfactory.model.entity.Rol;
import com.jmb.springfactory.rol.RolFactory;
import com.jmb.springfactory.rol.RolSamples;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class RolDaoImplTest {

  private RolDaoImpl rolDaoImpl;
  
  private Rol rolSample;
  private Rol alreadyRolSample;
  
  @Mock
  private RolRepository rolRepository;
  
  @SuppressWarnings("unchecked")
  @Before
  public void setUp() {
    rolDaoImpl = new RolDaoImpl();
    rolSample = RolFactory.createSampleDefaultRol();
    alreadyRolSample = RolFactory.createSampleRol(RolSamples.ID_ROL_TEST_2, RolSamples.NAME_ROL_TEST_2);

    when(rolRepository.save(any(Rol.class))).thenReturn(rolSample);
    when(rolRepository.save(alreadyRolSample)).thenThrow(PersistenceLayerException.class);
  }
  
  @Test
  public void whenSaveEntitySaveMethodRepositoryIsCalled() {
    rolDaoImpl.save(rolSample);
    verify(rolRepository).save(rolSample);
  }
  
  @Test
  public void whenSaveEntitySuccesfullyShouldReturnRol() {
    final Rol savedRol = rolDaoImpl.save(rolSample);

    assertNotNull(savedRol);
    assertEquals(savedRol, rolSample);
  }
  
  @Test(expected = PersistenceLayerException.class)
  public void whenEntityAlreadyExistsShouldThrowPersistenceLayerException() {
    rolDaoImpl.save(alreadyRolSample);    
  }
}
