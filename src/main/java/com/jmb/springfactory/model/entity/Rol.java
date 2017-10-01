package com.jmb.springfactory.model.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import org.springframework.data.mongodb.core.index.Indexed;

import com.jmb.springfactory.model.enumeration.PermissionsEnum;

import lombok.Data;

@Data
@Entity
@Table
public class Rol extends BaseEntity{

  @Indexed(unique = true)
  private String name;

  @Enumerated(EnumType.STRING)
  private List<PermissionsEnum> permissions;
}
