package com.jmb.springfactory.model.dto;

import javax.validation.constraints.NotNull;

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
}
