package com.jmb.springfactory.exceptions;

public class NotFoundException extends Exception {

  private static final long serialVersionUID = -7835455020535088700L;
  
  public NotFoundException() {
    super();
  }
  
  public NotFoundException(String message) {
    super(message);
  }

}
