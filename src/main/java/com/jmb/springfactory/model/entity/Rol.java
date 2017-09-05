package com.jmb.springfactory.model.entity;

import java.util.List;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import org.springframework.data.mongodb.core.index.Indexed;

import com.jmb.springfactory.model.Enum.PermissionsEnum;

import lombok.Data;

@Data
public class Rol extends BaseEntity{

  @Indexed(unique = true)
  private String name;

  @Enumerated(EnumType.STRING)
  private List<PermissionsEnum> permissions;
}
