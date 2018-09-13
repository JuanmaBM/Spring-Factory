package com.jmb.springfactory.model.factory.rol;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.jmb.springfactory.model.entity.Permission;
import com.jmb.springfactory.model.entity.Rol;
import com.jmb.springfactory.model.enumeration.PermissionsEnum;

import lombok.val;

public final class RolFactory {
  
  private RolFactory() {}

  public static Rol createRol(Integer id, String name, List<Permission> permissions) {
    final Rol rol = new Rol();
    rol.setId(id);
    rol.setName(name);
    rol.setPermissions(permissions);
    return rol;
  }
  
  public static Rol createSampleDefaultRol() {
    return createRol(RolSamples.ID_ROL_TEST_1, RolSamples.NAME_ROL_TEST_1, createPermissions());
  }

  public static List<Permission> createPermissions() {
    val permission = new Permission();
    permission.setId(1);
    permission.setName(PermissionsEnum.MANAGE_TASK);
    return Arrays.asList(permission);
  }
  
  public static Stream<Rol> createStreamSampleDefaultRoles() {
    return Stream.of(createRol(RolSamples.ID_ROL_TEST_1, RolSamples.NAME_ROL_TEST_1, createPermissions()),
        createRol(RolSamples.ID_ROL_TEST_2, RolSamples.NAME_ROL_TEST_2, createPermissions()),
        createRol(RolSamples.ID_ROL_TEST_3, RolSamples.NAME_ROL_TEST_3, createPermissions()));
  }
  
  public static List<Rol> createListSampleDefaultRoles() {
    return createStreamSampleDefaultRoles().collect(Collectors.toList());
  }
  
}
