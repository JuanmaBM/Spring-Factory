package com.jmb.springfactory.model.factory.user;

import com.jmb.springfactory.model.entity.Rol;
import com.jmb.springfactory.model.entity.User;

import static com.jmb.springfactory.model.factory.user.UserSamples.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class UserFactory {
  
  private UserFactory() {}

  public static User createUser(String nif, String name, String surname, String phoneNumber, String email, Rol rol) {
    return User.builder().nif(nif).name(name).surname(surname).phoneNumber(phoneNumber).email(email).rol(rol).build();
  }
  
  public static User createSampleDefaultUser() {
    return createUser(NIF_USER_TEST_1, NAME_USER_TEST_1, SURNAME_USER_TEST_1, PHONENUMBER_USER_TEST_1, 
        EMAIL_USER_TEST_1, ROL_USER_TEST_1);
  }
  
  public static Stream<User> createSampleDefaultStreamUser() {
    return Stream.of(
        createUser(NIF_USER_TEST_1, NAME_USER_TEST_1, SURNAME_USER_TEST_1, PHONENUMBER_USER_TEST_1, EMAIL_USER_TEST_1,
            ROL_USER_TEST_1),
        createUser(NIF_USER_TEST_2, NAME_USER_TEST_2, SURNAME_USER_TEST_2, PHONENUMBER_USER_TEST_2, EMAIL_USER_TEST_2, 
            ROL_USER_TEST_2), 
        createUser(NIF_USER_TEST_3, NAME_USER_TEST_3, SURNAME_USER_TEST_3, PHONENUMBER_USER_TEST_3, EMAIL_USER_TEST_3, 
            ROL_USER_TEST_3));
  }
  
  public static List<User> createSampleDefaultListUser() {
    return createSampleDefaultStreamUser().collect(Collectors.toList());
  }
}
