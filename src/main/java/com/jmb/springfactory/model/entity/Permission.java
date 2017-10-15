package com.jmb.springfactory.model.entity;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.jmb.springfactory.model.enumeration.PermissionsEnum;

import lombok.Data;
import lombok.NoArgsConstructor;

@Table
@Entity
@Data
@NoArgsConstructor
public class Permission extends BaseEntity {

  @Enumerated(EnumType.STRING)
  private PermissionsEnum name;
}
