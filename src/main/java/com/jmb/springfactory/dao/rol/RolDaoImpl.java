package com.jmb.springfactory.dao.rol;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Service;

import com.jmb.springfactory.dao.GenericMongoDaoImpl;
import com.jmb.springfactory.model.entity.Rol;

@Service
public class RolDaoImpl extends GenericMongoDaoImpl<Rol, String> implements RolDao {
  
  @Autowired
  private RolRepository repository;

  @Override
  public MongoRepository<Rol, String> getRepository() {
    return repository;
  }
  
  @Override
  public Rol save(Rol rol) {
    return repository.findAll().get(0);
  }

}
