package com.jmb.springfactory.model.entity;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

import com.jmb.springfactory.model.enumeration.PriorityEnum;
import com.jmb.springfactory.model.enumeration.TaskStatusEnum;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Task extends BaseEntity {

  private String name;

  private String description;

  private Double estimatedTime;

  private TaskStatusEnum status;

  private LocalDate startDate;

  private LocalDate finishDate;

  private PriorityEnum priority;

  private User creator;

  private String reasonRejection;

  private Integer orderNumber;

  @OneToMany(fetch = FetchType.LAZY)
  private List<Comment> comments;

  @OneToMany(fetch = FetchType.LAZY)
  private List<WorkLog> workLogs;

}
