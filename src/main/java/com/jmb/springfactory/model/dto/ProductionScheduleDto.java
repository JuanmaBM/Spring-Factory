package com.jmb.springfactory.model.dto;

import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class ProductionScheduleDto extends BaseDto {

  private static final long serialVersionUID = -8477427436054169331L;

  private String name;
  private Date estimatedStartDate;
  private Date estimatedFinishDate;
  private Date realStartDate;
  private Date realFinishDate;
  private String state;
}
