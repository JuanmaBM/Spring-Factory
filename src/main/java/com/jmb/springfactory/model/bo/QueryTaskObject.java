package com.jmb.springfactory.model.bo;

import java.util.Date;

import com.jmb.springfactory.model.enumeration.PriorityEnum;
import com.jmb.springfactory.model.enumeration.TaskStatusEnum;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class QueryTaskObject {

  private String name;
  private TaskStatusEnum status;
  private Date startDate;
  private Date finishDate;
  private PriorityEnum priority;
  private String creator;
}
