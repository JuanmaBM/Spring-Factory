package com.jmb.springfactory.service;

import java.util.List;

import org.apache.log4j.Logger;

import com.jmb.springfactory.exceptions.NotFoundException;

public final class UtilsService extends BaseService {

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

}
