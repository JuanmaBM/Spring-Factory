package com.jmb.springfactory.service.rol;

import java.util.List;

import com.jmb.springfactory.exceptions.NotFoundException;
import com.jmb.springfactory.model.dto.RolDto;
import com.jmb.springfactory.service.GenericService;

public interface RolService extends GenericService<RolDto, String> {

  public List<RolDto> findByNameContain(String name) throws NotFoundException;
}
