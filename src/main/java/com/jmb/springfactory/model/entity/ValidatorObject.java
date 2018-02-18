package com.jmb.springfactory.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ValidatorObject {
  private Object entity;
  private String modifierUser;
}
