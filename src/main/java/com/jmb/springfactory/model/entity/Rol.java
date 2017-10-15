package com.jmb.springfactory.model.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;


import lombok.Data;

@Data 
@Entity
@Table
public class Rol extends BaseEntity {

  private String name;

  @OneToMany(fetch = FetchType.LAZY)
  private List<Permission> permissions;
}
