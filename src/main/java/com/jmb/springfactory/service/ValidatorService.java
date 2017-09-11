package com.jmb.springfactory.service;

public interface ValidatorService {

  /**
   * Validate object from the specific context of service that implement this interface
   * @param object
   */
  public void validate(Object object);
}
