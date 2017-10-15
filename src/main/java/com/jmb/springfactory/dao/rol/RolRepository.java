package com.jmb.springfactory.dao.rol;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jmb.springfactory.model.entity.Rol;

@Repository
public interface RolRepository extends JpaRepository<Rol, Integer>{

}
