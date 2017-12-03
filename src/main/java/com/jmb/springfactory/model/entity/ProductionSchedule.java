package com.jmb.springfactory.model.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

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
  
  @Temporal(TemporalType.TIMESTAMP)
  private Date estimatedStartDate;
  
  @Temporal(TemporalType.TIMESTAMP)
  private Date estimatedFinishDate;
  
  @Temporal(TemporalType.TIMESTAMP)
  private Date realStartDate;
  
  @Temporal(TemporalType.TIMESTAMP)
  private Date realFinishDate;
  
  private ProductionScheduleStateEnum state;
  
  @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  private List<ProductionOrder> orders;
}
