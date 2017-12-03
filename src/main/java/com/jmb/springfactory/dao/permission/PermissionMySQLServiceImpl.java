package com.jmb.springfactory.dao.permission;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jmb.springfactory.model.entity.Permission;
import com.jmb.springfactory.model.enumeration.PermissionsEnum;


@Repository
public class PermissionMySQLServiceImpl {

  @Autowired
  private PermissionRepository permissionRepository;
  
  @PostConstruct
  public void init() {
    
    if (permissionRepository.count() == 0) {
      saveDefaultPermission();
    }
  }

  private void saveDefaultPermission() {

    for (PermissionsEnum permissionName : PermissionsEnum.values()) {
      Permission permission = new Permission();
      permission.setName(permissionName);

      permissionRepository.save(permission);
    }
  }
}
