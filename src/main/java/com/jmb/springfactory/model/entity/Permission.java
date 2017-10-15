package com.jmb.springfactory.model.entity;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import com.jmb.springfactory.model.enumeration.PermissionsEnum;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Table
@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class Permission extends BaseEntity {

  @Enumerated(EnumType.STRING)
  private PermissionsEnum name;
}
