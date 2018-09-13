package com.jmb.springfactory.service.application;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ApplicationServiceImpl implements ApplicationService {

//  FIXME: Comprobar porque no encuentra el path del fichero de configuracion en el context de test
//  @Value("${version}")
  private String version;
  
//  FIXME: Comprobar porque no encuentra el path del fichero de configuracion en el context de test
//  @Value("${name}")
  private String name;
  
  @Override
  public String getApplicationVersion() {
    return version;
  }
  
  @Override
  public String getApplicationName() {
    return name;
  }
}
