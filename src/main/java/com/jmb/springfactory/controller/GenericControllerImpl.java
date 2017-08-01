package com.jmb.springfactory.controller;

import java.io.Serializable;
import java.util.List;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.jmb.springfactory.exceptions.NotFoundException;
import com.jmb.springfactory.exceptions.ServiceLayerException;
import com.jmb.springfactory.model.dto.BaseDto;
import com.jmb.springfactory.service.GenericService;

public abstract class GenericControllerImpl<D extends BaseDto, ID extends Serializable> extends BaseController
    implements GenericController<D, ID> {
  
  public abstract GenericService<D, ID> genericService();

  @Override
  @PostMapping
  public D create(@Valid @RequestBody D dto) throws ServiceLayerException {
    return genericService().save(dto);
  }

  @Override
  @PutMapping("/{id}")
  public void update(@Valid @RequestBody D dto, @PathVariable("id") ID id) throws ServiceLayerException {
    genericService().update(dto, id);
  }

  @Override
  @DeleteMapping("/{id}")
  public void delete(@PathVariable("id") ID id) {
    genericService().delete(id);
  }

  @Override
  @GetMapping
  public List<D> findAll() {
    return genericService().findAll();
  }

  @Override
  @GetMapping("/{id}")
  public D findOne(@PathVariable("id") ID id) throws NotFoundException {
    return genericService().findOne(id);
  }

}
