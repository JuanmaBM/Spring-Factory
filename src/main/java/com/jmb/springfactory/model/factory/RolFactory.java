package com.jmb.springfactory.model.factory;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.jmb.springfactory.model.entity.Rol;

public final class RolFactory {

  public static Rol createRol(String id, String name) {
    final Rol rol = new Rol();
    rol.setId(id);
    rol.setName(name);
    return rol;
  }
  
  public static Rol createSampleDefaultRol() {
    return createRol(RolSamples.ID_ROL_TEST_1, RolSamples.NAME_ROL_TEST_1);
  }
  
  public static Stream<Rol> createStreamSampleDefaultRoles() {
    return Stream.of(createRol(RolSamples.ID_ROL_TEST_1, RolSamples.NAME_ROL_TEST_1),
        createRol(RolSamples.ID_ROL_TEST_2, RolSamples.NAME_ROL_TEST_2),
        createRol(RolSamples.ID_ROL_TEST_3, RolSamples.NAME_ROL_TEST_3));
  }
  
  public static List<Rol> createListSampleDefaultRoles() {
    return createStreamSampleDefaultRoles().collect(Collectors.toList());
  }
  
}
