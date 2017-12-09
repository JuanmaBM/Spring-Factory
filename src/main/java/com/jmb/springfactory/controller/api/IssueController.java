package com.jmb.springfactory.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jmb.springfactory.controller.GenericController;
import com.jmb.springfactory.model.dto.IssueDto;
import com.jmb.springfactory.service.GenericService;
import com.jmb.springfactory.service.issue.IssueService;

@RestController
@RequestMapping("/issue")
public class IssueController extends GenericController<IssueDto, Integer> {

  @Autowired
  private IssueService issueService;

  @Override
  public GenericService<IssueDto, Integer> genericService() {
    return issueService;
  }

}
