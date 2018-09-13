package com.jmb.springfactory.dao.permission;

import com.jmb.springfactory.dao.GenericMySQLService;
import com.jmb.springfactory.model.entity.Permission;
import com.jmb.springfactory.model.enumeration.PermissionsEnum;
import com.jmb.springfactory.service.GenericService;

public interface PermissionMySQLService extends GenericMySQLService<Permission, Integer> {

  public Permission findPermissionByName(PermissionsEnum name);
}
