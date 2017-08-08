package com.jmb.springfactory.service;

import java.io.Serializable;
import java.util.List;

import com.jmb.springfactory.exceptions.NotFoundException;
import com.jmb.springfactory.exceptions.ServiceLayerException;
import com.jmb.springfactory.model.dto.BaseDto;

public interface GenericService<D extends BaseDto, I extends Serializable> {

  /**
   * Pass the entity to dao to store it in bd
   * @param t
   * @return
   * @throws ServiceLayerException 
   */
  public D save(D t) throws ServiceLayerException;
  

  /**
   * Pass the entity to dao to update it in bd
   * @param t
   * @throws ServiceLayerException 
   */
  public void update(D t, I id) throws ServiceLayerException;
  
  /**
   * Search the entity by id and delete the entity in bd
   * @param id
   */
  public void delete(I id);
  
  /**
   * Return all entities
   * @return
   */
  public List<D> findAll();
  
  /**
   * Return a monad searching by id 
   * @param id
   * @return
   * @throws NotFoundException 
   */
  public D findOne(I id) throws NotFoundException;
  
}
