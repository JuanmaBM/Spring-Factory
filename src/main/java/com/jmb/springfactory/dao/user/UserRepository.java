package com.jmb.springfactory.dao.user;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jmb.springfactory.model.entity.User;

public interface UserRepository extends JpaRepository<User, String>{

}
