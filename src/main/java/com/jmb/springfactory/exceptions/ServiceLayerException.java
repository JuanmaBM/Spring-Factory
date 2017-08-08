package com.jmb.springfactory.exceptions;

public class ServiceLayerException extends Exception {

  private static final long serialVersionUID = -7024422279436750267L;

  public ServiceLayerException() {
    super();
  }

  public ServiceLayerException(String message) {
    super(message);
  }
}
