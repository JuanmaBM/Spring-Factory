package com.jmb.springfactory.model.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.jmb.springfactory.model.enumeration.IssueStatusEnum;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Issue extends BaseEntity {

  private String name;

  private String description;

  private IssueStatusEnum status;

  @ManyToOne(fetch = FetchType.LAZY)
  private User reporter;

  @ManyToOne(fetch = FetchType.LAZY)
  private User reviser;

  @OneToMany(fetch = FetchType.LAZY)
  private List<WorkLog> workLogs;

  @OneToMany(fetch = FetchType.LAZY)
  private List<Comment> comments;
}
