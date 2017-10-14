package com.jmb.springfactory.service.group;

import javax.validation.ValidationException;

import static org.apache.commons.lang3.StringUtils.isBlank;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.jmb.springfactory.dao.group.GroupMongoService;
import com.jmb.springfactory.model.dto.WorkGroupDto;
import com.jmb.springfactory.service.BaseService;
import com.jmb.springfactory.service.UtilsService;
import com.jmb.springfactory.service.ValidatorService;

@Service
@Qualifier("groupValidatorService")
public class GroupValidatorService extends BaseService implements ValidatorService {
  
  private static final String FIELD_FINISH_HOUR = "finishHour";
  private static final String FIELD_START_HOUR = "startHour";
  private static final String FIELD_NAME = "name";
  @Autowired
  private GroupMongoService groupMongoService;

  @Override
  public void validate(Object object) {
    
    final WorkGroupDto group = (WorkGroupDto) object;
    
    validateIfAnyFieldIsEmpty(group);
    
    validateIfAlreadyExistGroup(group);
  }
  
  private void validateIfAlreadyExistGroup(WorkGroupDto group) {
    
    final Boolean groupAlreadyExist = Optional.ofNullable(group)
      .map(WorkGroupDto::getName)
      .filter(StringUtils::isNotBlank)
      .flatMap(name -> groupMongoService.findByName(name).findAny())
      .isPresent();
    
    if (groupAlreadyExist) {
      throw new ValidationException("Groups must have unique names");
    }
  }

  private void validateIfAnyFieldIsEmpty(WorkGroupDto group) {
    
    final List<String> emptyFields = new ArrayList<>();
    
    if (isBlank(group.getName())) {
      emptyFields.add(FIELD_NAME);
    }

    if (isBlank(group.getStartHour())) {
      emptyFields.add(FIELD_START_HOUR);
    }
    
    if (isBlank(group.getFinishHour())){
      emptyFields.add(FIELD_FINISH_HOUR);
    }
    
    UtilsService.throwValidationExceptionIfEmptyFieldIsNotEmpty(emptyFields, serviceLog);
  }

}
