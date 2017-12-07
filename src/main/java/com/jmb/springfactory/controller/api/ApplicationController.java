package com.jmb.springfactory.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jmb.springfactory.service.application.ApplicationService;

@RestController
@RequestMapping("/api/app")
public class ApplicationController {

  @Autowired
  private ApplicationService applicationService;

  @GetMapping("/version")
  public String getVersion() {
    return applicationService.getApplicationVersion();
  }
  
  @GetMapping("/name")
  public String getName() {
    return applicationService.getApplicationName();
  }
}
