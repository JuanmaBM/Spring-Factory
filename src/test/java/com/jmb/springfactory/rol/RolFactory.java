package com.jmb.springfactory.rol;

import com.jmb.springfactory.model.entity.Rol;

public final class RolFactory {

  public static Rol createSampleRol(String id, String name) {
    final Rol rol = new Rol();
    rol.setId(id);
    rol.setName(name);
    return rol;
  }
  
  public static Rol createSampleDefaultRol() {
    return createSampleRol(RolSamples.ID_ROL_TEST_1, RolSamples.NAME_ROL_TEST_1);
  }
  
}
