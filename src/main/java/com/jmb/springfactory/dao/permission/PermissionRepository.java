package com.jmb.springfactory.dao.permission;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jmb.springfactory.model.entity.Permission;

public interface PermissionRepository extends JpaRepository<Permission, Integer> {

}
