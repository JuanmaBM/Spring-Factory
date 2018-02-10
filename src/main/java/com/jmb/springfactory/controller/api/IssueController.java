package com.jmb.springfactory.controller.api;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jmb.springfactory.controller.BaseController;
import com.jmb.springfactory.exceptions.NotFoundException;
import com.jmb.springfactory.exceptions.ServiceLayerException;
import com.jmb.springfactory.model.dto.IssueDto;
import com.jmb.springfactory.service.issue.IssueService;

import lombok.val;

@RestController
@RequestMapping("/issue")
public class IssueController extends BaseController {

  @Autowired
  private IssueService issueService;

  @PostMapping
  public IssueDto create(Authentication ath, @Valid @RequestBody IssueDto dto) throws ServiceLayerException {
    final val username = super.getUserName(ath);
    return issueService.save(dto, username);
  }

  @PutMapping("/{id}")
  public void update(@Valid @RequestBody IssueDto dto, @PathVariable("id") Integer id) throws ServiceLayerException {
    issueService.update(dto, id);
  }

  @DeleteMapping("/{id}")
  public void delete(@PathVariable("id") Integer id) {
    issueService.delete(id);
  }

  @GetMapping("/{id}")
  public IssueDto findOne(@PathVariable("id") Integer id) throws NotFoundException {
    return issueService.findOne(id);
  }
}
