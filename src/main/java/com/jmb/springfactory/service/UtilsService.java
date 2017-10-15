package com.jmb.springfactory.service;

import java.util.List;

import javax.validation.ValidationException;

import org.apache.log4j.Logger;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.GenericPropertyMatcher;

import com.jmb.springfactory.exceptions.NotFoundException;

public final class UtilsService extends BaseService {

  private static final String VALIDATION_EXCEPTION_MESSAGE = "The %s fields must not be empty";
  
  /**
   * Check if list is empty, if it's empty then throw a NotFoundException
   * @param list
   * @throws NotFoundException
   */
  public static void throwNotFoundExceptionIfEmptyList(List<?> list) throws NotFoundException {
    if (list.isEmpty()) {
      throw new NotFoundException();
    }
  }
  
  /**
   * Shows by log the entities in the parameter list 
   * @param list
   * @param log
   */
  public static void showEntitiesFoundInLog (List<?> list, Logger log) {

    log.info("Entities found:");
    list.forEach(entity -> log.info(entity));
  }
  
  /**
   * Auxiliar method that throw a exception with empty fields if there are any field empty
   * @param emptyFields
   */
  public static void throwValidationExceptionIfEmptyFieldIsNotEmpty(List<String> emptyFields, Logger log) {
    
    if (!emptyFields.isEmpty()) {
      log.error(String.format(VALIDATION_EXCEPTION_MESSAGE, emptyFields.toString()));
      throw new ValidationException(String.format(VALIDATION_EXCEPTION_MESSAGE, emptyFields.toString()));
    }
  }
  
  /**
   * Create a example matcher to search a entity that contains the value in field propertyName 
   * 
   * @param propertyName
   * @return
   */
  public static ExampleMatcher createMatcherContain(String propertyName) {

    if (propertyName == null) {
      return null;
    }

    return ExampleMatcher.matching().withMatcher(propertyName, GenericPropertyMatcher::contains);
  }

  /**
   * Create a example matcher to search a entity that contains the exact value in field propertyName 
   * 
   * @param propertyName
   * @return
   */
  public static ExampleMatcher createMatcherExact(String propertyName) {

    if (propertyName == null) {
      return null;
    }

    return ExampleMatcher.matching().withMatcher(propertyName, GenericPropertyMatcher::exact);
  }
  
  /**
   * Check if the object exists
   * @param obj
   * @return
   */
  public static Boolean exist(Object obj) {
    return obj != null;
  }
}
