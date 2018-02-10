package com.jmb.springfactory.service.issue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.jmb.springfactory.dao.GenericMySQLService;
import com.jmb.springfactory.dao.issue.IssueMySQLService;
import com.jmb.springfactory.exceptions.NotFoundException;
import com.jmb.springfactory.exceptions.ServiceLayerException;
import com.jmb.springfactory.model.bo.BusinessObjectBase;
import com.jmb.springfactory.model.dto.IssueDto;
import com.jmb.springfactory.model.entity.Issue;
import com.jmb.springfactory.service.GenericServiceImpl;
import com.jmb.springfactory.service.ValidatorService;
import com.jmb.springfactory.service.user.UserService;

@Service
public class IssueServiceImpl extends GenericServiceImpl<Issue, IssueDto, BusinessObjectBase, Integer> 
  implements IssueService {

  @Autowired
  private IssueMySQLService issueMySQLService;
  
  @Autowired
  private UserService userService;
  
  @Autowired
  @Qualifier("issueValidatorService")
  private ValidatorService issueValidatorService;

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

  @Override
  public IssueDto save(IssueDto issue, String userName) throws ServiceLayerException, NotFoundException {

    userService.findByNif(userName).ifPresent(issue::setReporter);
    issueValidatorService.validateOnCreate(issue);
    
    // Searchs a reviser and assigned the new issue
    // FIXME: Searh the issue manager who have less opened issues
    userService.findIssueManagerUsers().stream().findFirst().ifPresent(issue::setReviser);

    return save(issue);
  }

}
