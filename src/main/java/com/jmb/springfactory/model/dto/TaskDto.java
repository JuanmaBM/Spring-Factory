package com.jmb.springfactory.model.dto;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class TaskDto extends BaseDto {

  private static final long serialVersionUID = -3407898622118575169L;

  @NotNull
  private String name;

  @NotNull
  private String description;

  @NotNull
  private Double estimatedTime;

  private String status;

  @DateTimeFormat(iso = ISO.DATE_TIME)
  private Date startDate;

  @DateTimeFormat(iso = ISO.DATE_TIME)
  private Date finishDate;

  @NotNull
  private String priority;

  private UserDto creator;

  private String reasonRejection;

  private String blockedReason;

  private Integer orderNumber;

  private List<CommentDto> comments;

  private List<WorkLogDto> workLogs;

  @JsonIgnore
  private ProductionOrderDTO order;
  
  @JsonProperty(access = Access.WRITE_ONLY)
  private WorkGroupDto groupAssigned;

}
