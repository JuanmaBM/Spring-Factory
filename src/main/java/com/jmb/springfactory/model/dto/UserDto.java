package com.jmb.springfactory.model.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserDto extends BaseDto {

  private static final long serialVersionUID = 4524700632350624818L;
  private static final String VALIDATION_EMAIL_PATTERN = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$";

  @NotNull(message = "El campo nif no puede estar vacio")
  @Size(min = 9, max = 9, message = "El nif debe contener 9 caracteres")
  private String nif;

  @NotNull(message = "El campo nombre no puede estar vacio")
  private String name;

  @NotNull(message = "El campo apellidos no puede estar vacio")
  private String surname;

  @NotNull(message = "El campo telefono no puede estar vacio")
  @Size(min = 9, max = 9, message = "El campo telefeno debe contener 9 caracteres")
  private String phoneNumber;

  @Pattern(regexp = VALIDATION_EMAIL_PATTERN)
  private String email;
  
  private RolDto rol;
}
