package com.jmb.springfactory.dao.user;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.jmb.springfactory.model.entity.User;

public interface UserRepository extends MongoRepository<User, String>{

}
