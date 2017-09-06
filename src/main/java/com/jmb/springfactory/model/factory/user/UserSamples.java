package com.jmb.springfactory.model.factory.user;

import com.jmb.springfactory.model.dto.RolDto;
import com.jmb.springfactory.model.entity.Rol;
import com.jmb.springfactory.model.factory.rol.RolDtoFactory;
import com.jmb.springfactory.model.factory.rol.RolFactory;
import com.jmb.springfactory.model.factory.rol.RolSamples;

public final class UserSamples {
  
  private UserSamples() {}

  public static final String NIF_USER_TEST_1 = "12345678Z";
  public static final String NAME_USER_TEST_1 = "Juan";
  public static final String SURNAME_USER_TEST_1 = "Martin Perea";
  public static final String PHONENUMBER_USER_TEST_1 = "123456789";
  public static final String EMAIL_USER_TEST_1 = "juan@gmail.com";
  public static final Rol ROL_USER_TEST_1 = RolFactory.createRol(RolSamples.ID_ROL_TEST_1, RolSamples.NAME_ROL_TEST_1);
  public static final RolDto ROLDTO_USER_TEST_1 = RolDtoFactory.createRol(RolSamples.ID_ROL_TEST_1,
      RolSamples.NAME_ROL_TEST_1);

  public static final String NIF_USER_TEST_2 = "99999999R";
  public static final String NAME_USER_TEST_2 = "Paco";
  public static final String SURNAME_USER_TEST_2 = "Perez De La Rosa";
  public static final String PHONENUMBER_USER_TEST_2 = "999555666";
  public static final String EMAIL_USER_TEST_2 = "paco@gmail.com";
  public static final Rol ROL_USER_TEST_2 = RolFactory.createRol(RolSamples.ID_ROL_TEST_2, RolSamples.NAME_ROL_TEST_2);
  public static final RolDto ROLDTO_USER_TEST_2 = RolDtoFactory.createRol(RolSamples.ID_ROL_TEST_2,
      RolSamples.NAME_ROL_TEST_2);

  public static final String NIF_USER_TEST_3 = "88888888S";
  public static final String NAME_USER_TEST_3 = "Maria";
  public static final String SURNAME_USER_TEST_3 = "Lopez Tevez";
  public static final String PHONENUMBER_USER_TEST_3 = "123654345";
  public static final String EMAIL_USER_TEST_3 = "maria@gmail.com";
  public static final Rol ROL_USER_TEST_3 = RolFactory.createRol(RolSamples.ID_ROL_TEST_3, RolSamples.NAME_ROL_TEST_3);
  public static final RolDto ROLDTO_USER_TEST_3 = RolDtoFactory.createRol(RolSamples.ID_ROL_TEST_3,
      RolSamples.NAME_ROL_TEST_3);
}
