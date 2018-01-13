package com.jmb.springfactory.model.factory.user;

import static com.jmb.springfactory.model.factory.user.UserSamples.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.jmb.springfactory.model.dto.RolDto;
import com.jmb.springfactory.model.dto.UserDto;

public final class UserDtoFactory {

  private UserDtoFactory() {}
  
  public static UserDto createUserDto(String nif, String name, String surname, String phoneNumber, String email,
      RolDto rol, String password) {
    final UserDto user = new UserDto();
    user.setNif(nif);
    user.setName(name);
    user.setSurname(surname);
    user.setPhoneNumber(phoneNumber);
    user.setEmail(email);
    user.setRol(rol);
    user.setPassword(password);
    return user;
  }
  
  public static UserDto createSampleDefaultUserDto() {
    return createUserDto(NIF_USER_TEST_1, NAME_USER_TEST_1, SURNAME_USER_TEST_1, PHONENUMBER_USER_TEST_1, 
        EMAIL_USER_TEST_1, ROLDTO_USER_TEST_1, PASSWORD_USER_1);
  }

  public static Stream<UserDto> createSampleDefaultStreamUser() {
    return Stream.of(
        createUserDto(NIF_USER_TEST_1, NAME_USER_TEST_1, SURNAME_USER_TEST_1, PHONENUMBER_USER_TEST_1, EMAIL_USER_TEST_1,
            ROLDTO_USER_TEST_1, PASSWORD_USER_1),
        createUserDto(NIF_USER_TEST_2, NAME_USER_TEST_2, SURNAME_USER_TEST_2, PHONENUMBER_USER_TEST_2, EMAIL_USER_TEST_2, 
            ROLDTO_USER_TEST_2, PASSWORD_USER_2), 
        createUserDto(NIF_USER_TEST_3, NAME_USER_TEST_3, SURNAME_USER_TEST_3, PHONENUMBER_USER_TEST_3, EMAIL_USER_TEST_3, 
            ROLDTO_USER_TEST_3, PASSWORD_USER_3));
  }
  
  public static List<UserDto> createSampleDefaultListUser() {
    return createSampleDefaultStreamUser().collect(Collectors.toList());
  }
}
