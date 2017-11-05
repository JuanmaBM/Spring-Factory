package com.jmb.springfactory.model.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class ProductionOrderDTO extends BaseDto {

  private static final long serialVersionUID = 1038618099762504140L;

  private String name;
  private String description;
  private String measurements;
}
