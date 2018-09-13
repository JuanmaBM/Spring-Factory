package com.jmb.springfactory.model.bo;

import java.util.Date;

import com.jmb.springfactory.model.enumeration.StatusEnum;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class QueryProductionScheduleObject {

  private String name;
  private Date estimatedStartDate;
  private Date estimatedFinishDate;
  private Date realStartDate;
  private Date realFinishDate;
  private StatusEnum state;
  
  public Boolean isEmpty() {
      return name == null && estimatedStartDate == null && estimatedFinishDate == null 
              && realStartDate == null && realFinishDate == null && state == null;
  }
}
