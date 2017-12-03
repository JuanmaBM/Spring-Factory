package com.jmb.springfactory.service;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.jmb.springfactory.dao.GenericMySQLService;
import com.jmb.springfactory.exceptions.NotFoundException;
import com.jmb.springfactory.exceptions.PersistenceLayerException;
import com.jmb.springfactory.exceptions.ServiceLayerException;
import com.jmb.springfactory.model.bo.BusinessObjectBase;
import com.jmb.springfactory.model.dto.BaseDto;
import com.jmb.springfactory.model.entity.BaseEntity;

public abstract class GenericServiceImpl<T extends BaseEntity, D extends BaseDto, B extends BusinessObjectBase,
    I extends Serializable> extends GenericTransformerServiceImpl<T, D, B> implements GenericService<D, I> {

  private static final String DATABASE_ERROR_LOG = "A database error has ocurred: %s";

  public abstract GenericMySQLService<T, I> genericDao();
  
  @Override
  public D save(D dto) throws ServiceLayerException {
    return Optional.ofNullable(dto)
      .map(this::dtoToEntity)
      .map(saveEntity)
      .map(this::entityToDto)
      .orElseThrow(ServiceLayerException::new);
  }

  @Override
  public void update(D dto, I id) throws ServiceLayerException {
    dto.setId((Integer)id);
    genericDao().findOne(id)
      .map(entity -> merge(dto, entity))
      .map(saveEntity)
      .orElseThrow(ServiceLayerException::new);
  }

  @Override
  public void delete(I id) {
    genericDao().delete(id);
  }

  @Override
  public List<D> findAll() {
    return this.convertStreamEntityToStreamDto(genericDao().findAll())
        .collect(Collectors.toList());
  }

  @Override
  public D findOne(I id) throws NotFoundException {
    return genericDao().findOne(id)
        .map(this::entityToDto)
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
