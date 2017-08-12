package com.jmb.springfactory.model.factory;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.jmb.springfactory.model.dto.RolDto;

public class RolDtoFactory {

  public static RolDto createRol(String id, String name) {
    final RolDto rol = new RolDto();
    rol.setId(id);
    rol.setName(name);
    return rol;
  }
  
  public static RolDto createSampleDefaultRol() {
    return createRol(RolSamples.ID_ROL_TEST_1, RolSamples.NAME_ROL_TEST_1);
  }
  
  public static Stream<RolDto> createStreamSampleDefaultRoles() {
    return Stream.of(createRol(RolSamples.ID_ROL_TEST_1, RolSamples.NAME_ROL_TEST_1),
        createRol(RolSamples.ID_ROL_TEST_2, RolSamples.NAME_ROL_TEST_2),
        createRol(RolSamples.ID_ROL_TEST_3, RolSamples.NAME_ROL_TEST_3));
  }
  
  public List<RolDto> createListSampleDefaultRoles() {
    return createStreamSampleDefaultRoles().collect(Collectors.toList());
  }
}
