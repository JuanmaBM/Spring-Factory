package com.jmb.springfactory.dao.rol;

import java.util.stream.Stream;

import com.jmb.springfactory.dao.GenericMySQLService;
import com.jmb.springfactory.model.entity.Rol;

public interface RolMongoService extends GenericMySQLService<Rol, Integer>{

  public Rol save(Rol rol);

  Stream<Rol> findByNameContain(String nameRolTest1);
}
