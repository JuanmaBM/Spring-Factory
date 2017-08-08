package com.jmb.springfactory.controller;

import java.io.Serializable;
import java.util.List;

import com.jmb.springfactory.exceptions.NotFoundException;
import com.jmb.springfactory.exceptions.ServiceLayerException;
import com.jmb.springfactory.model.dto.BaseDto;

public interface GenericController<D extends BaseDto, I extends Serializable> {

  /**
   * Create a new resource 
   * @param dto
   * @return
   * @throws ServiceLayerException 
   */
  public D create(D dto) throws ServiceLayerException;
  
  /**
   * Update the resource identified by id with the data from dto
   * @param dto
   * @param id
   * @throws ServiceLayerException 
   */
  public void update(D dto, I id) throws ServiceLayerException;
  
  /**
   * Delete the resource identified by id
   * @param id
   */
  public void delete(I id);
  
  /**
   * Return a List with all resources of type D
   * @return
   */
  public List<D> findAll();
  
  /**
   * Return a resource of type D identified by id
   * @param id
   * @return
   * @throws NotFoundException 
   */
  public D findOne(I id) throws NotFoundException;

}
