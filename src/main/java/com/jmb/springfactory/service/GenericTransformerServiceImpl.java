package com.jmb.springfactory.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import com.jmb.springfactory.model.bo.BusinessObjectBase;
import com.jmb.springfactory.model.dto.BaseDto;
import com.jmb.springfactory.model.dto.PageDto;
import com.jmb.springfactory.model.entity.BaseEntity;

public abstract class GenericTransformerServiceImpl<T extends BaseEntity, D extends BaseDto, B extends BusinessObjectBase>
        extends BaseService implements GenericTransformerService<T, D, B> {

    @Autowired
    private ModelMapper modelMapper;

    public abstract Class<? extends T> getClazz();

    public abstract Class<? extends D> getDtoClazz();

    public abstract Class<? extends B> getBoClazz();

    public ModelMapper getMapper() {
        return modelMapper;
    }

    @Override
    public T boToEntity(B bo) {
        return modelMapper.map(bo, getClazz());
    }

    @Override
    public B entityToBo(T t) {
        return modelMapper.map(t, getBoClazz());
    }

    @Override
    public D boToDto(B bo) {
        return modelMapper.map(bo, getDtoClazz());
    }

    @Override
    public B dtoToBo(D dto) {
        return modelMapper.map(dto, getBoClazz());
    }

    @Override
    public T dtoToEntity(D dto) {
        return modelMapper.map(dto, getClazz());
    }

    @Override
    public D entityToDto(T entity) {
        return modelMapper.map(entity, getDtoClazz());
    }

    @Override
    public Stream<D> convertStreamEntityToStreamDto(Stream<T> entities) {
        return entities.map(entity -> modelMapper.map(entity, getDtoClazz()));
    }

    @Override
    public List<D> convertListEntityToListDto(List<T> entities) {
        return entities.stream().map(entity -> modelMapper.map(entity, getDtoClazz())).collect(Collectors.toList());
    }

    @Override
    public T merge(D dto, T entity) {
        final T copyEntity = entity;
        modelMapper.map(dto, copyEntity);
        return copyEntity;
    }

    @Override
    public PageDto<D> mapPageToPageDto(final Page<T> pageEntity) {

        final PageDto<D> pageDto = new PageDto<>();
        final Optional<Page<T>> maybePageEntity = Optional.ofNullable(pageEntity);

        maybePageEntity.map(Page::getContent).map(this::convertListEntityToListDto).ifPresent(pageDto::setList);
        maybePageEntity.map(Page::getNumber).ifPresent(pageDto::setPage);
        maybePageEntity.map(Page::getTotalPages).ifPresent(pageDto::setTotalPages);

        return pageDto;
    }
}
