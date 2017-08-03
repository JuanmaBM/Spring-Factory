package com.jmb.springfactory.service;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.jmb.springfactory.dao.GenericMongoDao;
import com.jmb.springfactory.exceptions.NotFoundException;
import com.jmb.springfactory.exceptions.PersistenceLayerException;
import com.jmb.springfactory.exceptions.ServiceLayerException;
import com.jmb.springfactory.model.dto.BaseDto;
import com.jmb.springfactory.model.entity.BaseEntity;

public abstract class GenericServiceImpl<T extends BaseEntity, D extends BaseDto, ID extends Serializable>
    extends GenericTransformerServiceImpl<T, D> implements GenericService<D, ID> {

  private static final String DATABASE_ERROR_LOG = "A database error has ocurred: %s";

  public abstract GenericMongoDao<T, ID> genericDao();
  
  @Override
  public D save(D dto) throws ServiceLayerException {
    return Optional.ofNullable(dto)
      .map(this::convertToEntity)
      .map(saveEntity)
      .map(this::convertToDto)
      .orElseThrow(ServiceLayerException::new);
  }

  @Override
  public void update(D dto, ID id) throws ServiceLayerException {
    genericDao().findOne(id)
      .map(entity -> merge(dto, entity))
      .map(saveEntity)
      .orElseThrow(ServiceLayerException::new);
  }

  @Override
  public void delete(ID id) {
    genericDao().delete(id);
  }

  @Override
  public List<D> findAll() {
    return this.convertStreamEntityToStreamDto(genericDao().findAll())
        .collect(Collectors.toList());
  }

  @Override
  public D findOne(ID id) throws NotFoundException {
    return genericDao().findOne(id)
        .map(this::convertToDto)
        .orElseThrow(NotFoundException::new);
  }
  
  private final Function<T, T> saveEntity = entity -> {        
    try {
      return genericDao().save(entity);
    } catch (PersistenceLayerException e) {
      serviceLog.error(String.format(DATABASE_ERROR_LOG, e.getMessage()));
    }
    return null;
  };

}
