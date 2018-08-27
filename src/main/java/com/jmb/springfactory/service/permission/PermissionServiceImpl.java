package com.jmb.springfactory.service.permission;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jmb.springfactory.dao.GenericMySQLService;
import com.jmb.springfactory.dao.permission.PermissionMySQLService;
import com.jmb.springfactory.model.bo.BusinessObjectBase;
import com.jmb.springfactory.model.dto.PermisionDto;
import com.jmb.springfactory.model.entity.Permission;
import com.jmb.springfactory.service.GenericServiceImpl;

@Service
public class PermissionServiceImpl extends GenericServiceImpl<Permission, PermisionDto, BusinessObjectBase, Integer>
        implements PermissionService {

    @Autowired
    private PermissionMySQLService permissionMySQLService;

    @Override
    public GenericMySQLService<Permission, Integer> genericDao() {
        return permissionMySQLService;
    }

    @Override
    public Class<? extends Permission> getClazz() {
        return Permission.class;
    }

    @Override
    public Class<? extends PermisionDto> getDtoClazz() {
        return PermisionDto.class;
    }

    @Override
    public Class<? extends BusinessObjectBase> getBoClazz() {
        return BusinessObjectBase.class;
    }

}
