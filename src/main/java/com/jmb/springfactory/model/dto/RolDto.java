package com.jmb.springfactory.model.dto;

import java.util.List;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class RolDto extends BaseDto {

  private static final long serialVersionUID = 8906176643020347221L;

  @NotNull
  private String name;
  private List<String> permissions;
}
