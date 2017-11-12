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
import static com.jmb.springfactory.service.UtilsService.*;
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
  public void validateOnCreate(Object object) {
    
    final WorkGroupDto group = (WorkGroupDto) object;
    
    serviceLog.info("Validating if any fields is empty");
    validateIfAnyFieldIsEmpty(group);

    serviceLog.info("Validating if group already exists");
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
    
    throwValidationExceptionIfEmptyFieldIsNotEmpty(emptyFields, serviceLog);
  }

  @Override
  public void validateOnUpdate(Object object) {
    // There are not validation to update
  }

  @Override
  public void validateOnDelete(Object object) {
    // There are not validation to delete
  }

}
