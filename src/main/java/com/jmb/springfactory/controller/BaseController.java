package com.jmb.springfactory.controller;

import java.util.Optional;

import org.apache.log4j.Logger;
import org.springframework.security.core.Authentication;

import lombok.val;


public abstract class BaseController {

  protected Logger controllerLog = Logger.getLogger(getClass());
  private static final String ANONYMOUS = "ANONYMOUS";
  
  protected String getUserName(final Authentication ath) {

    final val ifPrincipalIsPresent = Optional.ofNullable(ath).map(Authentication::getPrincipal).isPresent();

    if (ifPrincipalIsPresent) return ath.getName(); else return new String(ANONYMOUS);
  }
}
