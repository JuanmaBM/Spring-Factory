package com.jmb.springfactory.model.dto;

import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class WorkGroupDto extends BaseDto {

  private static final long serialVersionUID = -4696421304807574674L;

  @NotNull
  private String name;
  @Pattern(regexp = "([01]?[0-9]|2[0-3]):[0-5][0-9]")
  private String startHour;
  @Pattern(regexp = "([01]?[0-9]|2[0-3]):[0-5][0-9]")
  private String finishHour;
  
  @JsonProperty(access = Access.WRITE_ONLY)
  private List<ProductionOrderDTO> ordersAssigned;

}
