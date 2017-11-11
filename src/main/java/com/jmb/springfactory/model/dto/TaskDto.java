package com.jmb.springfactory.model.dto;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;

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

  @NotNull
  private Date startDate;

  @NotNull
  private Date finishDate;

  @NotNull
  private String priority;

  private UserDto creator;

  private String reasonRejection;

  private Integer orderNumber;

  private List<CommentDto> comments;

  private List<WorkLogDto> workLogs;
}
