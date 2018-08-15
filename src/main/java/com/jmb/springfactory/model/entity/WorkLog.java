package com.jmb.springfactory.model.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class WorkLog extends BaseEntity {

  private Double hoursWorked;

  private String description;
  
  @ManyToOne(fetch = FetchType.LAZY)
  private Task task;
  
  @ManyToOne(fetch = FetchType.LAZY)
  private WorkGroup group;

  @ManyToOne(fetch = FetchType.LAZY)
  private User author;

}
