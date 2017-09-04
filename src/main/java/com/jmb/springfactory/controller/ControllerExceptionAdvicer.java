package com.jmb.springfactory.controller;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.jmb.springfactory.exceptions.NotFoundException;
import com.jmb.springfactory.exceptions.PersistenceLayerException;
import com.jmb.springfactory.exceptions.ServiceLayerException;

@ControllerAdvice(basePackages = "com.jmb.springfactory.controller")
public class ControllerExceptionAdvicer {
  
  private static final  Integer INTERNAL_SERVER_ERROR_CODE = 500;
  private static final  Integer NOT_FOUND_CODE = 404;

  @ResponseBody
  @ExceptionHandler(PersistenceLayerException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ApiError serverError(PersistenceLayerException e) {
    return new ApiError(INTERNAL_SERVER_ERROR_CODE, e.getClass().getSimpleName().concat(getExceptionMessage(e)));
  }
  
  @ResponseBody
  @ExceptionHandler(ServiceLayerException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ApiError serverError(ServiceLayerException e) {
    return new ApiError(INTERNAL_SERVER_ERROR_CODE, e.getClass().getSimpleName().concat(getExceptionMessage(e)));
  }

  @ResponseBody
  @ExceptionHandler(NotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public ApiError notFound(NotFoundException e) {
    return new ApiError(NOT_FOUND_CODE, e.getClass().getSimpleName().concat(getExceptionMessage(e)));
  }
  
  private String getExceptionMessage(Exception exception) {
    return Optional.ofNullable(exception)
        .map(Exception::getMessage)
        .orElse("");
  }
}
