package com.jmb.springfactory.service.issue;

import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.jmb.springfactory.dao.GenericMySQLService;
import com.jmb.springfactory.dao.issue.IssueMySQLService;
import com.jmb.springfactory.exceptions.NotFoundException;
import com.jmb.springfactory.exceptions.ServiceLayerException;
import com.jmb.springfactory.model.bo.BusinessObjectBase;
import com.jmb.springfactory.model.dto.IssueDto;
import com.jmb.springfactory.model.dto.UserDto;
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
    // Although there are not any restrictions, we do the validation because maybe it could be created in the future
    issueValidatorService.validateOnCreate(issue);
    
    // Searchs a reviser and assigned the new issue
    findRevisorLessBusy().ifPresent(issue::setReviser);

    return save(issue);
  }
  
  @Override
  public void update(IssueDto issue, Integer id, String userName) throws ServiceLayerException {
    
    userService.findByNif(userName).ifPresent(issue::setReporter);
    issueValidatorService.validateOnUpdate(issue);
    super.update(issue, id);
  }
  
  /**
   * Retrieve the user that has got less issues assigned
   * @return
   * @throws NotFoundException
   */
  private Optional<UserDto> findRevisorLessBusy() throws NotFoundException {
    
    return userService.findIssueManagerUsers().stream()
       // Collects the users list into a map which the key is the user and the value is the number of issues that the 
       // user has got assigned
      .collect(Collectors.toMap(Function.identity(), 
          user -> issueMySQLService.countIssueByReviserNif(((UserDto) user).getNif())))
      .entrySet().parallelStream()
      // Sorted the entry by the number of issues, so that we have in first position the user with less isuess asigned
      .sorted(Map.Entry.comparingByValue())
      .findFirst()
      // Finally we are left with user object
      .map(Map.Entry::getKey);
  }

}
