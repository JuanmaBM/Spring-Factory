package com.jmb.springfactory.service.issue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jmb.springfactory.dao.GenericMySQLService;
import com.jmb.springfactory.dao.issue.IssueMySQLService;
import com.jmb.springfactory.model.bo.BusinessObjectBase;
import com.jmb.springfactory.model.dto.IssueDto;
import com.jmb.springfactory.model.entity.Issue;
import com.jmb.springfactory.service.GenericServiceImpl;

@Service
public class IssueServiceImpl extends GenericServiceImpl<Issue, IssueDto, BusinessObjectBase, Integer> 
  implements IssueService {

  @Autowired
  private IssueMySQLService issueMySQLService;

  @Override
  public GenericMySQLService<Issue, Integer> genericDao() {
    return issueMySQLService;
  }

  @Override
  public Class<? extends Issue> getClazz() {
    return Issue.class;
  }

  @Override
  public Class<? extends IssueDto> getDtoClazz() {
    return IssueDto.class;
  }

  @Override
  public Class<? extends BusinessObjectBase> getBoClazz() {
    return BusinessObjectBase.class;
  }

}
