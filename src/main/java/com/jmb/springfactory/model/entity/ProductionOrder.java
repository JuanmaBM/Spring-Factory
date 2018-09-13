package com.jmb.springfactory.model.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.jmb.springfactory.model.enumeration.Measurements;
import com.jmb.springfactory.model.enumeration.StatusEnum;

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
  
  @Enumerated(EnumType.STRING)
  private StatusEnum status;

  @OneToMany(fetch = FetchType.LAZY)
  private List<WorkGroup> groupsAssigned;

}
