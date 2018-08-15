package com.jmb.springfactory.model.dto;

import java.util.List;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class ProductionOrderDTO extends BaseDto {

  private static final long serialVersionUID = 1038618099762504140L;

  @NotNull
  private String name;

  @NotNull
  private String description;

  @NotNull
  private String measurements;
  
  private String status;

  @JsonProperty(access = Access.WRITE_ONLY)
  private List<WorkGroupDto> groupsAssigned;

}
