package com.jmb.springfactory.model.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data 
@EqualsAndHashCode(callSuper = false)
@Entity
@Table
public class Rol extends BaseEntity {

  private String name;

  @OneToMany(fetch = FetchType.LAZY)
  private List<Permission> permissions;
}
