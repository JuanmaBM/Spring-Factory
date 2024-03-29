package com.jmb.springfactory.model.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class UserDto extends BaseDto {

  private static final long serialVersionUID = 2773559742318503229L;

  private static final String VALIDATION_EMAIL_PATTERN = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z]{2,6}$";

  @NotNull(message = "El campo nif no puede estar vacio")
  @Size(min = 9, max = 9, message = "El nif debe contener 9 caracteres")
  private String nif;
  
  @Size(min = 4)
  @JsonProperty(access = Access.WRITE_ONLY)
  private String password;

  @NotNull(message = "El campo nombre no puede estar vacio")
  private String name;

  @NotNull(message = "El campo apellidos no puede estar vacio")
  private String surname;

  @NotNull(message = "El campo telefono no puede estar vacio")
  @Size(min = 9, max = 9, message = "El campo telefeno debe contener 9 caracteres")
  private String phoneNumber;

  // FIXME: Pattern not work corretly
  //@Pattern(regexp = VALIDATION_EMAIL_PATTERN)
  private String email;
  
  private RolDto rol;
  
  private WorkGroupDto group;
}
