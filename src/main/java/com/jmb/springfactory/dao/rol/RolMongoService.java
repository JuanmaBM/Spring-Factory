package com.jmb.springfactory.dao.rol;

import java.util.stream.Stream;

import com.jmb.springfactory.dao.GenericMongoService;
import com.jmb.springfactory.model.entity.Rol;

public interface RolMongoService extends GenericMongoService<Rol, String>{

  public Rol save(Rol rol);

  Stream<Rol> findByNameContain(String nameRolTest1);
}
