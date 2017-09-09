package com.jmb.springfactory.model.entity;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Document
public class User extends BaseEntity{
  
  private static final String VALIDATION_EMAIL_PATTERN = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$";

  @NotNull
  @Size(min = 9, max = 9)
  private String nif;

  @NotNull
  private String name;

  @NotNull
  private String surname;

  @NotNull
  @Size(min = 9, max = 9)
  private String phoneNumber;

  @Pattern(regexp = VALIDATION_EMAIL_PATTERN)
  private String email;
  
  @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  private Rol rol;
}
