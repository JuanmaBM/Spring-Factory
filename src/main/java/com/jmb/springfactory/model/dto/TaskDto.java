package com.jmb.springfactory.model.dto;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class TaskDto extends BaseDto {

  private static final long serialVersionUID = -3407898622118575169L;

  @NotNull
  private String name;

  @NotNull
  @Max(value = 255, message = "El campo descripcion tiene un limite de 255 caracteres")
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

  @NotNull
  private UserDto creator;

  private String reasonRejection;

  private Integer orderNumber;

  private List<CommentDto> comments;

  private List<WorkLogDto> workLogs;

  @JsonIgnore
  private ProductionOrderDTO order;

}
