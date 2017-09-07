package com.jmb.springfactory.service.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jmb.springfactory.dao.GenericMongoService;
import com.jmb.springfactory.dao.user.UserMongoService;
import com.jmb.springfactory.model.bo.BusinessObjectBase;
import com.jmb.springfactory.model.dto.UserDto;
import com.jmb.springfactory.model.entity.User;
import com.jmb.springfactory.service.GenericServiceImpl;

@Service
public class UserServiceImpl extends GenericServiceImpl<User, UserDto, BusinessObjectBase, String>
  implements UserService{
  
  @Autowired
  private UserMongoService userMongoService;

  @Override
  public List<UserDto> findByNifContain(String nif) {
    return null;
  }

  @Override
  public List<UserDto> findByNameContain(String name) {
    return null;
  }

  @Override
  public GenericMongoService<User, String> genericDao() {
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
