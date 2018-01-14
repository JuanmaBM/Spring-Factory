package com.jmb.springfactory.dao.permission;

import com.jmb.springfactory.model.entity.Permission;
import com.jmb.springfactory.model.enumeration.PermissionsEnum;

public interface PermissionMySQLService {

  public Permission findPermissionByName(PermissionsEnum name);
  public Permission findOne(Integer id);
}
