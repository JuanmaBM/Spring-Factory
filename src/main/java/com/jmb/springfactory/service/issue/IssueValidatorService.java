package com.jmb.springfactory.service.issue;

import java.util.Optional;
import java.util.function.Predicate;

import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.jmb.springfactory.dao.issue.IssueMySQLService;
import com.jmb.springfactory.model.dto.IssueDto;
import com.jmb.springfactory.model.dto.PermisionDto;
import com.jmb.springfactory.model.dto.RolDto;
import com.jmb.springfactory.model.dto.UserDto;
import com.jmb.springfactory.model.entity.Issue;
import com.jmb.springfactory.model.enumeration.PermissionsEnum;
import com.jmb.springfactory.service.BaseService;
import com.jmb.springfactory.service.ValidatorService;

@Service
@Qualifier("issueValidatorService")
public class IssueValidatorService extends BaseService implements ValidatorService {
  
  @Autowired
  private IssueMySQLService issueMySQLService;

  private Predicate<IssueDto> reporterHasPermissionToManageIssues = issue -> Optional.ofNullable(issue)
      .map(IssueDto::getReporter)
      .map(UserDto::getRol)
      .map(RolDto::getPermissions)
      .map(permissions -> permissions.stream().map(PermisionDto::getName))
      .map(permissionNames -> permissionNames.anyMatch(permission -> PermissionsEnum.MANAGE_ISSUE.name().equals(permission)))
      .orElse(Boolean.FALSE);
      

  @Override
  public void validateOnCreate(Object object) {
    // There are not any rules to create
  }

  @Override
  public void validateOnUpdate(Object object) {

    final IssueDto issue = (IssueDto) object;

    if (!reporterHasPermissionToManageIssues.test(issue)) {

      final Issue storedIssue = issueMySQLService.findOne(issue.getId())
          .orElseThrow(() -> new ValidationException("The issue not exists yet"));
      
      if (!storedIssue.getStatus().name().equals(issue.getStatus())) {
        throw new ValidationException("Only users with manages issue permission can change the state of issues");
      }
    }
    
  }

  @Override
  public void validateOnDelete(Object object) {

    final IssueDto issue = (IssueDto) object;
    if (!reporterHasPermissionToManageIssues.test(issue)) {
      throw new ValidationException("Only user with manages issue permission can created issues");
    }
  }


}
