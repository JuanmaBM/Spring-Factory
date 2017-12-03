package com.jmb.springfactory.service.user;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.jmb.springfactory.dao.GenericMySQLService;
import com.jmb.springfactory.dao.user.UserMongoService;
import com.jmb.springfactory.exceptions.NotFoundException;
import com.jmb.springfactory.exceptions.ServiceLayerException;
import com.jmb.springfactory.model.bo.BusinessObjectBase;
import com.jmb.springfactory.model.dto.UserDto;
import com.jmb.springfactory.model.entity.User;
import com.jmb.springfactory.service.GenericServiceImpl;
import com.jmb.springfactory.service.UtilsService;
import com.jmb.springfactory.service.ValidatorService;

@Service
public class UserServiceImpl extends GenericServiceImpl<User, UserDto, BusinessObjectBase, Integer>
  implements UserService{
  
  @Autowired
  private UserMongoService userMongoService;
  
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
