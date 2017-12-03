package com.jmb.springfactory.service;

public interface ValidatorService {

  /**
   * Validate the creation object from the specific context of service that implement this interface
   * @param object
   */
  public void validateOnCreate(Object object);

  /**
   * Validate the update object from the specific context of service that implement this interface
   * @param object
   */
  public void validateOnUpdate(Object object);

  /**
   * Validate the delete object from the specific context of service that implement this interface
   * @param object
   */
  public void validateOnDelete(Object object);
}
