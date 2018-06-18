package com.jmb.springfactory.model.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.jmb.springfactory.model.enumeration.ProductionScheduleStateEnum;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@Entity
@Table
public class ProductionSchedule extends BaseEntity {

  private String name;
  
  private Date estimatedStartDate;
  
  private Date estimatedFinishDate;
  
  private Date realStartDate;
  
  private Date realFinishDate;
  
  @Enumerated(EnumType.STRING)
  private ProductionScheduleStateEnum state;
}
