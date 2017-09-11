package com.jmb.springfactory.model.entity;

import java.util.List;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.jmb.springfactory.model.enumeration.PermissionsEnum;

import lombok.Data;

@Data
@Document
public class Rol extends BaseEntity{

  @Indexed(unique = true)
  private String name;

  @Enumerated(EnumType.STRING)
  private List<PermissionsEnum> permissions;
}
