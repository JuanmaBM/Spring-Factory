package com.jmb.springfactory.model.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class PermisionDto extends BaseDto {

  private static final long serialVersionUID = -3597965386587437015L;
  private Integer id;
  private String name;
}
