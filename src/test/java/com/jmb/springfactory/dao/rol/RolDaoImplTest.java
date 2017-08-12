package com.jmb.springfactory.dao.rol;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
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
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;

import com.jmb.springfactory.dao.rol.RolMongoServiceImpl;
import com.jmb.springfactory.dao.rol.RolRepository;
import com.jmb.springfactory.exceptions.PersistenceLayerException;
import com.jmb.springfactory.model.entity.Rol;
import com.jmb.springfactory.model.factory.RolFactory;
import com.jmb.springfactory.model.factory.RolSamples;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class RolDaoImplTest {

  @InjectMocks
  private RolMongoServiceImpl rolDaoImpl;

  private Rol rolSample;
  private Rol alreadyRolSample;
  private List<Rol> listRolSample;

  @Mock
  private RolRepository rolRepository;

  @SuppressWarnings("unchecked")
  @Before
  public void setUp() {
    rolSample = RolFactory.createSampleDefaultRol();
    alreadyRolSample = RolFactory.createRol(RolSamples.ID_ROL_TEST_2, RolSamples.NAME_ROL_TEST_2);
    listRolSample = RolFactory.createListSampleDefaultRoles();

    when(rolRepository.save(any(Rol.class))).thenReturn(rolSample);
    when(rolRepository.save(alreadyRolSample)).thenThrow(PersistenceLayerException.class);
    when(rolRepository.findAll(any(Example.class))).thenReturn(listRolSample);
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

  @SuppressWarnings("unchecked")
  @Test
  public void whenSearchEntityByExampleMethodRepositoryIsCalled() {
    rolDaoImpl.findByNameContain(RolSamples.NAME_ROL_TEST_1);
    verify(rolRepository).findAll(any(Example.class));
  }

  @Test
  public void whenItSearchByNameShouldReturnStream() {
    final Stream<Rol> rolesFounded = rolDaoImpl.findByNameContain(RolSamples.NAME_ROL_TEST_1);

    assertNotNull(rolesFounded);
    assertEquals(rolesFounded.count(), listRolSample.size());
  }
}
