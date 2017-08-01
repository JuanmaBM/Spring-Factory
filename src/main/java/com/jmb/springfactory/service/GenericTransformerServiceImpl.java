package com.jmb.springfactory.service;

import java.util.stream.Stream;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import com.jmb.springfactory.model.dto.BaseDto;
import com.jmb.springfactory.model.entity.BaseEntity;

public abstract class GenericTransformerServiceImpl<T extends BaseEntity, D extends BaseDto>
    implements GenericTransformerService<T, D>{
  
  @Autowired
  private ModelMapper modelMapper;
  
  public abstract Class<? extends T> getClazz();
  
  public abstract Class<? extends D> getDtoClazz();

  @Override
  public T convertToEntity(D dto) {
    return modelMapper.map(dto, getClazz());
  }

  @Override
  public D convertToDto(T entity) {
    return modelMapper.map(entity, getDtoClazz());
  }

  @Override
  public Stream<D> convertStreamEntityToStreamDto(Stream<T> entities) {
    return entities.map(entity -> modelMapper.map(entity, getDtoClazz()));
  }

  @Override
  public T merge(D dto, T entity) {
    final T copyEntity = entity;
    modelMapper.map(dto, copyEntity);
    return copyEntity;
  }

}
