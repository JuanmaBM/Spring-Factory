package com.jmb.springfactory.service.rol;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jmb.springfactory.dao.GenericMongoService;
import com.jmb.springfactory.dao.rol.RolMongoService;
import com.jmb.springfactory.model.bo.BusinessObjectBase;
import com.jmb.springfactory.model.dto.RolDto;
import com.jmb.springfactory.model.entity.Rol;
import com.jmb.springfactory.service.GenericServiceImpl;

@Service
public class RolServiceImpl extends GenericServiceImpl<Rol, RolDto, BusinessObjectBase, String>
  implements RolService {
  
  @Autowired
  private RolMongoService rolMongoService;

  @Override
  public GenericMongoService<Rol, String> genericDao() {
    return rolMongoService;
  }

  @Override
  public Class<? extends Rol> getClazz() {
    return Rol.class;
  }

  @Override
  public Class<? extends RolDto> getDtoClazz() {
    return RolDto.class;
  }

  @Override
  public Class<? extends BusinessObjectBase> getBoClazz() {
    return BusinessObjectBase.class;
  }


}
