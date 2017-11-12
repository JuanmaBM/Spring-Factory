package com.jmb.springfactory.service.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jmb.springfactory.dao.task.TaskMySQLService;
import com.jmb.springfactory.model.dto.TaskDto;
import com.jmb.springfactory.model.entity.Task;
import com.jmb.springfactory.model.entity.User;

import static com.jmb.springfactory.service.UtilsService.*;

import java.util.Optional;
import java.util.function.Predicate;

import javax.validation.ValidationException;

import com.jmb.springfactory.service.BaseService;
import com.jmb.springfactory.service.ValidatorService;

@Service
public class TaskValidatorService extends BaseService implements ValidatorService {

  @Autowired
  private TaskMySQLService taskMySQLService;
  
  @Override
  public void validate(Object obj) {
    
    final TaskDto taskDto = (TaskDto) obj;
    throwValidationExceptionIfTaskIsNull(taskDto);
    
    serviceLog.info("Validating if create user has not changed");
    if (creatorHasChanged.test(taskDto)) 
      throw new ValidationException("The task creator cant be changed");
  }

  final Predicate<TaskDto> creatorHasChanged = taskDto -> Optional.ofNullable(taskDto.getId())
      .flatMap(taskMySQLService::findOne)
      .map(Task::getCreator)
      .map(User::getId)
      .map(userId -> userId.equals(taskDto.getCreator().getId()))
      .orElse(Boolean.FALSE);

  private void throwValidationExceptionIfTaskIsNull(final TaskDto taskDto) {
    if (notExist(taskDto)) 
      throw new ValidationException("The task object must not be null");
  }

}
