package com.jmb.springfactory.service.user;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jmb.springfactory.dao.GenericMySQLService;
import com.jmb.springfactory.dao.group.GroupMongoService;
import com.jmb.springfactory.dao.rol.RolMongoService;
import com.jmb.springfactory.dao.user.UserMongoService;
import com.jmb.springfactory.exceptions.NotFoundException;
import com.jmb.springfactory.exceptions.ServiceLayerException;
import com.jmb.springfactory.model.bo.BusinessObjectBase;
import com.jmb.springfactory.model.dto.PermisionDto;
import com.jmb.springfactory.model.dto.RolDto;
import com.jmb.springfactory.model.dto.UserDto;
import com.jmb.springfactory.model.dto.WorkGroupDto;
import com.jmb.springfactory.model.entity.Permission;
import com.jmb.springfactory.model.entity.Rol;
import com.jmb.springfactory.model.entity.User;
import com.jmb.springfactory.model.entity.WorkGroup;
import com.jmb.springfactory.service.GenericServiceImpl;
import com.jmb.springfactory.service.UtilsService;
import com.jmb.springfactory.service.ValidatorService;

import lombok.val;

@Service
public class UserServiceImpl extends GenericServiceImpl<User, UserDto, BusinessObjectBase, Integer>
        implements UserService {

    @Autowired
    private UserMongoService userMongoService;

    @Autowired
    private RolMongoService rolMongoService;

    @Autowired
    private GroupMongoService groupMongoService;

    @Autowired
    @Qualifier("userValidatorService")
    private ValidatorService userValidatorService;

    @Override
    public List<UserDto> findByNifContain(String nif) throws NotFoundException {

        serviceLog.info(String.format("Search user which contain nif %s", nif));
        final List<User> usersFound = userMongoService.findByNifContain(nif).collect(Collectors.toList());

        UtilsService.throwNotFoundExceptionIfEmptyList(usersFound);
        UtilsService.showEntitiesFoundInLog(usersFound, serviceLog);

        return this.convertListEntityToListDto(usersFound);
    }

    @Override
    public List<UserDto> findByNameContain(String name) throws NotFoundException {

        serviceLog.info(String.format("Search user which contain name %s", name));
        final List<User> usersFound = userMongoService.findByNameContain(name).collect(Collectors.toList());

        UtilsService.throwNotFoundExceptionIfEmptyList(usersFound);
        UtilsService.showEntitiesFoundInLog(usersFound, serviceLog);

        return this.convertListEntityToListDto(usersFound);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<UserDto> findByNif(String nif) {

        serviceLog.info(String.format("Search user with nif %s", nif));
        return Optional.ofNullable(nif).flatMap(userMongoService::findByNif).map(this::entityToDto);
    }

    @Override
    public UserDto save(UserDto userToSave) throws ServiceLayerException {
        userValidatorService.validateOnCreate(userToSave);
        Optional.ofNullable(userToSave).map(UserDto::getPassword).map(DigestUtils::sha1Hex)
                .ifPresent(userToSave::setPassword);
        return super.save(userToSave);
    }

    @Override
    @Transactional
    public void update(UserDto userToUpdate, Integer id) throws ServiceLayerException {
        userValidatorService.validateOnUpdate(userToUpdate);
        super.update(userToUpdate, id);
    }

    @Override
    public User merge(UserDto dto, User entity) {

        if (UtilsService.existAll(dto, entity)) {
            mapUserDetails(dto, entity);
            mapUserRolDetails(dto, entity);
            mapUserGroupDetails(dto, entity);
        }

        return entity;
    }

    private void mapUserGroupDetails(UserDto dto, User entity) {

        final Optional<Integer> idGroupDto = Optional.ofNullable(dto).map(UserDto::getGroup).map(WorkGroupDto::getId);
        val storedGroupId = Optional.ofNullable(entity).map(User::getGroup).map(WorkGroup::getId).orElse(null);
        val dtoHaveGroup = idGroupDto.isPresent();
        val areNotTheSameGroup = dtoHaveGroup && !idGroupDto.get().equals(storedGroupId);

        if (!dtoHaveGroup) {
            entity.setGroup(null);
        } else if (areNotTheSameGroup) {
            idGroupDto.flatMap(groupMongoService::findOne).ifPresent(entity::setGroup);
        }
    }

    private void mapUserRolDetails(UserDto dto, User entity) {

        final Optional<Integer> idRolDto = Optional.ofNullable(dto).map(UserDto::getRol).map(RolDto::getId);
        val idRolStored = Optional.ofNullable(entity).map(User::getRol).map(Rol::getId).orElse(null);
        val areNotTheSameRole = idRolDto.isPresent() && !idRolDto.get().equals(idRolStored);

        if (areNotTheSameRole)
            idRolDto.flatMap(rolMongoService::findOne).ifPresent(entity::setRol);
    }

    private void mapUserDetails(UserDto dto, User entity) {

        if (UtilsService.exist(entity) && UtilsService.exist(dto)) {
            entity.setEmail(dto.getEmail());
            entity.setName(dto.getName());
            entity.setNif(dto.getNif());
            entity.setPhoneNumber(dto.getPhoneNumber());
            entity.setSurname(dto.getSurname());
        }
    }

    @Override
    public List<PermisionDto> getPermission(final String nif) {

        final Function<Permission, PermisionDto> permissionToPermissionDto = permission -> getMapper().map(permission,
                PermisionDto.class);

        final List<Permission> permissions = Optional.ofNullable(nif).flatMap(userMongoService::findByNif)
                .map(User::getRol).map(Rol::getPermissions).orElse(Collections.emptyList());

        return permissions.stream().map(permissionToPermissionDto).collect(Collectors.toList());
    }

    @Override
    public Integer getGroupId(final String nif) {

        return Optional.ofNullable(nif).flatMap(userMongoService::findByNif).map(User::getGroup).map(WorkGroup::getId)
                .orElse(null);
    }

    @Override
    public GenericMySQLService<User, Integer> genericDao() {
        return userMongoService;
    }

    @Override
    public Class<? extends User> getClazz() {
        return User.class;
    }

    @Override
    public Class<? extends UserDto> getDtoClazz() {
        return UserDto.class;
    }

    @Override
    public Class<? extends BusinessObjectBase> getBoClazz() {
        return BusinessObjectBase.class;
    }

}
