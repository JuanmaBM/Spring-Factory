package com.jmb.springfactory.dao.rol;

import java.util.stream.Stream;

import com.jmb.springfactory.dao.GenericMongoDao;
import com.jmb.springfactory.model.entity.Rol;

public interface RolDao extends GenericMongoDao<Rol, String>{

  public Rol save(Rol rol);

  Stream<Rol> findByNameContain(String nameRolTest1);
}
