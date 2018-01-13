package com.jmb.springfactory.dao.permission;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Repository;

import com.jmb.springfactory.model.entity.Permission;
import com.jmb.springfactory.model.enumeration.PermissionsEnum;

import lombok.val;


@Repository
public class PermissionMySQLServiceImpl implements PermissionMySQLService {

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

  @Override
  public Permission findPermissionByName(PermissionsEnum name) {

    final val permission = new Permission();
    permission.setName(name);

    final val permissionExample = Example.of(permission);

    return permissionRepository.findOne(permissionExample);
  }
  
  @Override
  public Permission findOne(Integer id) {
    return permissionRepository.findOne(id);
  }
  
}
