package com.jmb.springfactory.service.application;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ConfigurationProperties(prefix = "spring")
@Component
public class InfoApplicationComponent {

  private String version;
  private String name;

}
