package com.jmb.springfactory.dao.rol;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.GenericPropertyMatcher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.jmb.springfactory.dao.GenericMySQLServiceImpl;
import com.jmb.springfactory.dao.permission.PermissionMySQLService;
import com.jmb.springfactory.model.entity.Rol;
import com.jmb.springfactory.model.factory.rol.RolFactory;

import lombok.val;

@Service
public class RolMongoServiceImpl extends GenericMySQLServiceImpl<Rol, Integer> implements RolMongoService {
  
  @Autowired
  private RolRepository repository;
  
  @Autowired
  private PermissionMySQLService permissionMySQLService;

  @Override
  public JpaRepository<Rol, Integer> getRepository() {
    return repository;
  }
  
  @Override
  public Rol save(Rol rol) {
    
    // Retrieve permission from BD to get hibernete proxy session
    val permissions = rol.getPermissions().stream()
      .map(permission -> {
        return permissionMySQLService.findPermissionByName(permission.getName()); 
      })
      .collect(Collectors.toList());        
    
    rol.setPermissions(permissions);

    return repository.save(rol);
  }

  @Override
  public Stream<Rol> findByNameContain(String name) {
    final ExampleMatcher matcher = ExampleMatcher.matching()
        .withMatcher("name", GenericPropertyMatcher::contains);
    final Example<Rol> rolByNameExample = Example.of(RolFactory.createRol(null, name, null), matcher);

    return repository.findAll(rolByNameExample).stream();
  }  

}
