package com.jmb.springfactory.controller.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jmb.springfactory.controller.GenericController;
import com.jmb.springfactory.model.dto.WorkGroupDto;
import com.jmb.springfactory.service.GenericService;
import com.jmb.springfactory.service.group.GroupService;

@RestController
@RequestMapping("/group")
public class GroupController extends GenericController<WorkGroupDto, String> {
  
  @Autowired
  private GroupService groupService;

  @Override
  public GenericService<WorkGroupDto, String> genericService() {
    return groupService;
  }
  
  @GetMapping
  public List<WorkGroupDto> findAll() {
    return groupService.findAll();
  }

}
