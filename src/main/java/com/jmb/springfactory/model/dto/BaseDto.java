package com.jmb.springfactory.model.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class BaseDto implements Serializable {

  private static final long serialVersionUID = 4243036072511510879L;

  private Integer id;
}
