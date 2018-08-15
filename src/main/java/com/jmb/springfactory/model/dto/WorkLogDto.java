package com.jmb.springfactory.model.dto;

import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class WorkLogDto extends BaseDto {

  private static final long serialVersionUID = 6369266544916427726L;

  @NotNull
  private Double hoursWorked;
  @NotNull
  private String description;
  
  private WorkGroupDto group;
  
  private UserDto author;
}
