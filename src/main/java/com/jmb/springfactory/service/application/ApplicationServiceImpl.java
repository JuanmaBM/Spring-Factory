package com.jmb.springfactory.service.application;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ApplicationServiceImpl implements ApplicationService {

  @Value("${version}")
  private String version;
  
  @Value("${name}")
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
