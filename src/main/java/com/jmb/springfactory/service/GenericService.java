package com.jmb.springfactory.service;

import java.io.Serializable;
import java.util.List;

import com.jmb.springfactory.exceptions.NotFoundException;
import com.jmb.springfactory.exceptions.ServiceLayerException;

public interface GenericService<T, D, ID extends Serializable> {

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
  public void update(D t, ID id) throws ServiceLayerException;
  
  /**
   * Search the entity by id and delete the entity in bd
   * @param id
   */
  public void delete(ID id);
  
  /**
   * Return all entities
   * @return
   */
  public List<T> findAll();
  
  /**
   * Return a monad searching by id 
   * @param id
   * @return
   * @throws NotFoundException 
   */
  public T findOne(ID id) throws NotFoundException;
  
  /**
   * To map an entity to DTO
   * @param t
   * @return
   */
  public D convertToDto(T t);
  
  /**
   * To map a DTO to entity
   * @param d
   * @return
   */
  public T convertToEntity(D d);

}
