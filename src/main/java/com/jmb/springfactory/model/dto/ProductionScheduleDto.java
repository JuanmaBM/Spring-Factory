package com.jmb.springfactory.model.dto;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class ProductionScheduleDto extends BaseDto {

  private static final long serialVersionUID = -8477427436054169331L;

  @NotNull
  private String name;

  @NotNull
  @DateTimeFormat(iso = ISO.DATE)
  private Date estimatedStartDate;

  @NotNull
  @DateTimeFormat(iso = ISO.DATE)
  private Date estimatedFinishDate;

  @DateTimeFormat(iso = ISO.DATE)
  private Date realStartDate;

  @DateTimeFormat(iso = ISO.DATE)
  private Date realFinishDate;

  private String state;
  private List<ProductionOrderDTO> orders;
}
