package com.jmb.springfactory.model.entity;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.jmb.springfactory.model.enumeration.PriorityEnum;
import com.jmb.springfactory.model.enumeration.TaskStatusEnum;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Task extends BaseEntity {

  private String name;

  private String description;

  private Double estimatedTime;

  private TaskStatusEnum status;

  private Date startDate;

  private Date finishDate;

  private PriorityEnum priority;

  @ManyToOne(fetch = FetchType.LAZY)
  private User creator;

  private String reasonRejection;

  private String blockedReason;

  private Integer orderNumber;

  // TODO: cambiar por one to one
  @ManyToOne(fetch = FetchType.LAZY)
  private ProductionOrder order;
  
  @ManyToOne(fetch = FetchType.LAZY)
  private WorkGroup groupAssigned;

}
