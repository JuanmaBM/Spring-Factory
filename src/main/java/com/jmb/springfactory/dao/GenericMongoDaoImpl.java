package com.jmb.springfactory.dao;

import java.io.Serializable;
import java.util.Optional;
import java.util.stream.Stream;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.jmb.springfactory.exceptions.PersistenceLayerException;
import com.jmb.springfactory.model.entity.BaseEntity;

public abstract class GenericMongoDaoImpl<T extends BaseEntity, ID extends Serializable> extends BaseDao implements 
  GenericMongoDao<T, ID> {
  
  public abstract MongoRepository<T, ID> getRepository();

  @Override
  public T save(T t) throws PersistenceLayerException {

    checkIfEntityExists(t);
    return this.getRepository().save(t);
  }

  @Override
  public void delete(ID id) {
    this.getRepository().delete(id);
  }

  @Override
  public Stream<T> findAll() {
    return this.getRepository().findAll().stream();
  }

  @Override
  public Optional<T> findOne(ID id) {
    return Optional.ofNullable(this.getRepository().findOne(id));
  }

  private void checkIfEntityExists(T t) throws PersistenceLayerException {
    Optional.ofNullable(t)
      .orElseThrow(PersistenceLayerException::new);
  }

}
