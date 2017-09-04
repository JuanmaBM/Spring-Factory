package com.jmb.springfactory.service.rol;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jmb.springfactory.dao.GenericMongoService;
import com.jmb.springfactory.dao.rol.RolMongoService;
import com.jmb.springfactory.exceptions.NotFoundException;
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

  @Override
  public List<RolDto> findByNameContain(String name) throws NotFoundException {

    serviceLog.info("Searching roles with name equals to " + name);
    final List<Rol> foundRoles = rolMongoService.findByNameContain(name).collect(Collectors.toList());

    throwNotFoundExceptionIfEmptyList(foundRoles);
    serviceLog.info("Have been found " + foundRoles.size() + " roles");

    serviceLog.info("Transforming Roles to Dtos");
    return this.convertListEntityToListDto(foundRoles);
  }

  private void throwNotFoundExceptionIfEmptyList(List<Rol> roles) throws NotFoundException {
    if (roles.isEmpty()) {
      serviceLog.info("No rol found");
      throw new NotFoundException();
    }
  }

}
