package com.jmb.springfactory.model.entity;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Document
public class Group extends BaseEntity{

  private String name;
  private String startHour;
  private String finishHour;
}
