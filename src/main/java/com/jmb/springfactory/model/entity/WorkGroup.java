package com.jmb.springfactory.model.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@Entity
@Table
public class WorkGroup extends BaseEntity{

  private String name;
  private String startHour;
  private String finishHour;
  
  @ManyToMany(fetch = FetchType.LAZY)
  private List<ProductionOrder> ordersAssigned;
  
}
