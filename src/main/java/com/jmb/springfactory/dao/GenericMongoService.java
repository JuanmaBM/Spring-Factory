package com.jmb.springfactory.dao;

import java.io.Serializable;
import java.util.Optional;
import java.util.stream.Stream;

import com.jmb.springfactory.exceptions.PersistenceLayerException;
import com.jmb.springfactory.model.entity.BaseEntity;

public interface GenericMongoService<T extends BaseEntity, I extends Serializable> {

  /**
   * Store the entity in the BD
   * @param t
   * @throws PersistenceLayerException 
   */
  public T save(T t) throws PersistenceLayerException;

  /**
   * Search by his id and delete the entity t from the BD 
   * @param id
   */
  public void delete(I id);
  
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
  public Optional<T> findOne(I id);

}
