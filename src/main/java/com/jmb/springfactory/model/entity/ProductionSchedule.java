package com.jmb.springfactory.model.entity;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.jmb.springfactory.model.enumeration.StatusEnum;

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
  
  private StatusEnum state;
}
