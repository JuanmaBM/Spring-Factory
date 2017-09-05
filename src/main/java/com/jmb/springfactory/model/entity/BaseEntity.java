package com.jmb.springfactory.model.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;

@Document
public class BaseEntity {

  @Id
  @Getter
  @Setter
  private String id;
}
