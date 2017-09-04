package com.jmb.springfactory.dao.rol;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.jmb.springfactory.model.entity.Rol;

@Repository
public interface RolRepository extends MongoRepository<Rol, String>{

}
