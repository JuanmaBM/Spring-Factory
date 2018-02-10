package com.jmb.springfactory.service.issue;

import com.jmb.springfactory.exceptions.NotFoundException;
import com.jmb.springfactory.exceptions.ServiceLayerException;
import com.jmb.springfactory.model.dto.IssueDto;
import com.jmb.springfactory.service.GenericService;

public interface IssueService extends GenericService<IssueDto, Integer> {
  
  public IssueDto save(IssueDto issue, String userName) throws ServiceLayerException, NotFoundException;

}
