package com.jmb.springfactory.service.user;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.jmb.springfactory.dao.GenericMySQLService;
import com.jmb.springfactory.dao.permission.PermissionMySQLService;
import com.jmb.springfactory.dao.user.UserMongoService;
import com.jmb.springfactory.exceptions.NotFoundException;
import com.jmb.springfactory.exceptions.ServiceLayerException;
import com.jmb.springfactory.model.bo.BusinessObjectBase;
import com.jmb.springfactory.model.dto.UserDto;
import com.jmb.springfactory.model.entity.User;
import com.jmb.springfactory.model.enumeration.PermissionsEnum;
import com.jmb.springfactory.service.GenericServiceImpl;
import com.jmb.springfactory.service.UtilsService;
import com.jmb.springfactory.service.ValidatorService;

@Service
public class UserServiceImpl extends GenericServiceImpl<User, UserDto, BusinessObjectBase, Integer>
  implements UserService{
  
  @Autowired
  private UserMongoService userMongoService;
  
  @Autowired
  private PermissionMySQLService permissionMySQLService;
  
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
  public Optional<UserDto> findByNif(String nif) {

    serviceLog.info(String.format("Search user with nif %s", nif));
    return Optional.ofNullable(nif)
        .flatMap(userMongoService::findByNif)
        .map(this::entityToDto);
  }

  @Override
  public UserDto save(UserDto userToSave) throws ServiceLayerException {
    userValidatorService.validateOnCreate(userToSave);
    return super.save(userToSave);
  }

  @Override 
  public void update(UserDto userToUpdate, Integer id) throws ServiceLayerException {
    userToUpdate.setId(id);
    userValidatorService.validateOnUpdate(userToUpdate);
    super.update(userToUpdate, id);
  }
  
  private Stream<UserDto> findByPermission(final PermissionsEnum permission) throws NotFoundException {

    return Optional.ofNullable(permissionMySQLService.findPermissionByName(permission))
      .map(userMongoService::findByPermission)
      .map(this::convertStreamEntityToStreamDto)
      .orElseThrow(() -> 
        new NotFoundException(String.format("Not exists any permission with name %s", permission.name())));
  }
  
  @Override
  public List<UserDto> findIssueManagerUsers() throws NotFoundException {

    final List<UserDto> users = findByPermission(PermissionsEnum.MANAGE_ISSUE).collect(Collectors.toList());
    if (users.isEmpty()) throw new NotFoundException(); else return users;
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
