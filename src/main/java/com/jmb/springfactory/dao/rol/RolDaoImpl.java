package com.jmb.springfactory.dao.rol;

import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Service;

import com.jmb.springfactory.dao.GenericMongoDaoImpl;
import com.jmb.springfactory.model.entity.Rol;
import com.jmb.springfactory.model.factory.RolFactory;

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
    return repository.save(rol);
  }

  @Override
  public Stream<Rol> findByNameContain(String name) {
    final Example<Rol> rolByNameExample = Example.of(RolFactory.createRol(null, name));
//    return repository.findAll(rolByNameExample).stream();
    return null;
  }  

}
