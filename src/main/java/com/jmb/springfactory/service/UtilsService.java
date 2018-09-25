package com.jmb.springfactory.service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.validation.ValidationException;

import org.apache.log4j.Logger;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.GenericPropertyMatcher;

import com.jmb.springfactory.exceptions.NotFoundException;

public final class UtilsService extends BaseService {

  private static final String THE_ENTITY_WAS_CREATED_SUCCESFULLY = "The entity was created succesfully: %s";
  private static final String THE_ENTITY_WAS_UPDATED_SUCCESFULLY = "The entity was updated succesfully: %s";
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
   * Add a new element into the list param if this exists, if not exists, create a new list and append the new element
   * @param orders
   * @param order
   * @return
   */
  public static List<?> addIntoList(List<?> orders, Object order) {
    
    if (orders == null) {
      return Arrays.asList(order);
    }

    final List<Object> copyOrders = new ArrayList<>(orders);
    copyOrders.add(order);
    
    return copyOrders;
  }
  
  /**
   * Shows by log the entities in the parameter list 
   * @param list
   * @param log
   */
  public static void showEntitiesFoundInLog (List<?> list, Logger log) {

    log.info("Entities found:");
    list.forEach(log::info);
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
  
  public static Boolean existAll(Object ... obj) {
      Boolean allObjectExists = true;
      for (int i = 0; i < obj.length && allObjectExists; i++) {
          if (!exist(obj)) allObjectExists = false;
      }
      return allObjectExists;
  }
  
  /**
   * Deny the parameter value 
   * @param value
   * @return
   */
  public static Boolean not(Boolean value) {
    return exist(value) ? !value : false;
  }
  
  /**
   * Check if the object not exists
   * @param obj
   * @return
   */
  public static Boolean notExist(Object obj) {
    return not(exist(obj));
  }
  
  public static void logCreatedEntity(Object entity, Logger log) {
    log.info(String.format(THE_ENTITY_WAS_CREATED_SUCCESFULLY, entity.toString()));
  }

  public static void logUpdatedEntity(Object entity, Logger log) {
    log.info(String.format(THE_ENTITY_WAS_UPDATED_SUCCESFULLY, entity.toString()));
  }
  
  /**
   * Transform a Date to a LocalDate entity
   * @param date
   * @return
   */
  public static LocalDate dateToLocalDate(Date date) {
    return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
  }
}
