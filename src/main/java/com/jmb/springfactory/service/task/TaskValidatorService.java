package com.jmb.springfactory.service.task;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.jmb.springfactory.model.dto.TaskDto;
import static com.jmb.springfactory.service.UtilsService.*;

import javax.validation.ValidationException;

import com.jmb.springfactory.service.BaseService;
import com.jmb.springfactory.service.ValidatorService;

@Service
@Qualifier("taskValidatorService")
public class TaskValidatorService extends BaseService implements ValidatorService {

  
  @Override
  public void validateOnCreate(Object obj) {
    
    final TaskDto taskDto = (TaskDto) obj;
    generalValidate(taskDto);
  }
  
  private void generalValidate(TaskDto taskDto) {
    throwValidationExceptionIfTaskIsNull(taskDto);
  }

  private void throwValidationExceptionIfTaskIsNull(final TaskDto taskDto) {
    if (notExist(taskDto)) 
      throw new ValidationException("The task object must not be null");
  }

  @Override
  public void validateOnUpdate(Object obj) {

    final TaskDto taskDto = (TaskDto) obj;
    generalValidate(taskDto);

    serviceLog.info("Validating if create user has not changed");
  }

  @Override
  public void validateOnDelete(Object object) {
    // There are not rules on delete task
  }

}
