package com.jmb.springfactory.model.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table
public class Group extends BaseEntity{

  private String name;
  private String startHour;
  private String finishHour;
}
