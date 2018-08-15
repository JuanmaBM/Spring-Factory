package com.jmb.springfactory.model.dto;

import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class CommentDto extends BaseDto {

  private static final long serialVersionUID = 1317203685416529556L;

  @NotNull
  private UserDto author;
  
  private WorkGroupDto group;

  @NotNull
  @Size(min = 4, max = 255, message = "El campo comentario tiene un limite de 255 caracteres")
  private String text;

  @DateTimeFormat(iso = ISO.DATE)
  private Date creationDate;

  @DateTimeFormat(iso = ISO.DATE)
  private Date modificationDate;
}
