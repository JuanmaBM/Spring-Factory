package com.jmb.springfactory.dao.group;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.jmb.springfactory.model.entity.Group;

public interface GroupRepository extends MongoRepository<Group, String> {

}
