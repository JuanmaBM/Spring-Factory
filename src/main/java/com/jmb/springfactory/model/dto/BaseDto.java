package com.jmb.springfactory.model.dto;

import java.io.Serializable;

public class BaseDto implements Serializable {

  private static final long serialVersionUID = 5739080497948854510L;

  private String id;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }
}
