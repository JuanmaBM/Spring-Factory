package com.jmb.springfactory.model.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class WorkLog extends BaseEntity {

  private Double hoursWorked;

  @OneToOne(fetch = FetchType.LAZY)
  private Comment comment;
  
  @ManyToOne(fetch = FetchType.LAZY)
  private Task task;

}
