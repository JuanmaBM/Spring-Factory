package com.jmb.springfactory.dao;

import java.io.Serializable;
import java.util.Optional;
import java.util.stream.Stream;

import com.jmb.springfactory.exceptions.PersistenceLayerException;

public interface GenericMongoDao<T, ID extends Serializable> {

  /**
   * Store the entity in the BD
   * @param t
   * @throws PersistenceLayerException 
   */
  public void save(T t) throws PersistenceLayerException;
  
  /**
   * Update the data of entity t in BD. If t not exists, then it be created 
   * @param t
   * @return
   * @throws PersistenceLayerException 
   */
  public void update(T t) throws PersistenceLayerException;
  
  /**
   * Search and delete the entity t from the BD
   * @param t
   * @throws PersistenceLayerException 
   */
  public void delete(T t) throws PersistenceLayerException;
  
  /**
   * Search by his id and delete the entity t from the BD 
   * @param id
   */
  public void delete(ID id);
  
  /**
   * Retrieve all the object from a specific type of entity from BD in stream format
   * @return
   */
  public Stream<T> findAll();
  
  /**
   * Retrieve a Monad (Optional) of a specific entity by his id
   * @param id
   * @return
   */
  public Optional<T> findOne(ID id);

}
