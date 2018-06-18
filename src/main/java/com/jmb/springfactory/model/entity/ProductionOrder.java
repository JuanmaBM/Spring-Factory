package com.jmb.springfactory.model.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

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
  
  @ManyToOne(fetch = FetchType.LAZY)
  private ProductionSchedule productionSchedule;
  
  // TODO: Añadir estado

}
