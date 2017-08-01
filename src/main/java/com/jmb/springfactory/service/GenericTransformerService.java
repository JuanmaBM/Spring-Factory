package com.jmb.springfactory.service;

import java.util.stream.Stream;

import com.jmb.springfactory.model.dto.BaseDto;
import com.jmb.springfactory.model.entity.BaseEntity;

public interface GenericTransformerService<T extends BaseEntity, D extends BaseDto> {
  
  public T convertToEntity(D dto);
  
  public D convertToDto(T entity);
  
  public T merge(D dto, T entity);

  public Stream<D> convertStreamEntityToStreamDto(Stream<T> entities);

}
