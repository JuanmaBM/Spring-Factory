package com.jmb.springfactory.dao.permission;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jmb.springfactory.dao.GenericMySQLServiceImpl;
import com.jmb.springfactory.model.entity.Permission;
import com.jmb.springfactory.model.enumeration.PermissionsEnum;

import lombok.val;

@Repository
public class PermissionMySQLServiceImpl extends GenericMySQLServiceImpl<Permission, Integer>
        implements PermissionMySQLService {

    @Autowired
    private PermissionRepository permissionRepository;

    @PostConstruct
    public void init() {

        if (permissionRepository.count() != PermissionsEnum.values().length) {
            saveDefaultPermission();
        }
    }

    private void saveDefaultPermission() {

        final List<PermissionsEnum> definedPermissions = Arrays.asList(PermissionsEnum.values());
        final List<PermissionsEnum> storedPermissions = permissionRepository.findAll().stream().map(Permission::getName)
                .collect(Collectors.toList());
        final Predicate<PermissionsEnum> noContainsInStored = permission -> !storedPermissions.contains(permission);

        definedPermissions.stream().filter(noContainsInStored).forEach(createPermission);
    }

    private Consumer<PermissionsEnum> createPermission = permission -> {
        final Permission newPermission = new Permission();
        newPermission.setName(permission);

        permissionRepository.save(newPermission);
    };

    @Override
    public Permission findPermissionByName(PermissionsEnum name) {

        final val permission = new Permission();
        permission.setName(name);

        final val permissionExample = Example.of(permission);

        return permissionRepository.findOne(permissionExample);
    }

    @Override
    public JpaRepository<Permission, Integer> getRepository() {
        return permissionRepository;
    }

}
