package com.jmb.springfactory.model.entity;

import javax.persistence.Entity;

import com.jmb.springfactory.model.enumeration.Measurements;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class ProductionOrder extends BaseEntity {

  private String name;

  private String description;

  private Measurements measurements;

}
