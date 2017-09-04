package com.jmb.springfactory.service;

import java.util.List;
import java.util.stream.Stream;

import com.jmb.springfactory.model.bo.BusinessObjectBase;
import com.jmb.springfactory.model.dto.BaseDto;
import com.jmb.springfactory.model.entity.BaseEntity;

public interface GenericTransformerService<T extends BaseEntity, D extends BaseDto, B extends BusinessObjectBase> {
  
  public T boToEntity(B bo);
  
  public B entityToBo(T t);
  
  public D boToDto(B bo);
  
  public B dtoToBo(D dto);
  
  public T dtoToEntity(D dto);

  public D entityToDto(T entity);

  public T merge(D dto, T entity);

  public Stream<D> convertStreamEntityToStreamDto(Stream<T> entities);

  List<D> convertListEntityToListDto(List<T> entities);

}
