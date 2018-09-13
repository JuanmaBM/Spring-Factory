package com.jmb.springfactory.controller.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jmb.springfactory.model.dto.PermisionDto;
import com.jmb.springfactory.service.permission.PermissionService;

@RestController
@RequestMapping("/permission")
public class PermissionController  {

    @Autowired
    private PermissionService permissionService;

    @GetMapping
    public List<PermisionDto> findAll() {
        return permissionService.findAll();
    }
}
