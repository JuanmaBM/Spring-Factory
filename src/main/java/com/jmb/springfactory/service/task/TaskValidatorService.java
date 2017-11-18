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
  public void validateOnCreate(Object obj) {
    
    final TaskDto taskDto = (TaskDto) obj;
    generalValidate(taskDto);
  }
  
  private void generalValidate(TaskDto taskDto) {
    throwValidationExceptionIfTaskIsNull(taskDto);
  }

  final Predicate<TaskDto> creatorHasChanged = taskDto -> Optional.ofNullable(taskDto.getId())
      .flatMap(taskMySQLService::findOne)
      .map(Task::getCreator)
      .map(User::getId)
      .map(userId -> !userId.equals(taskDto.getCreator().getId()))
      .orElse(Boolean.TRUE);

  private void throwValidationExceptionIfTaskIsNull(final TaskDto taskDto) {
    if (notExist(taskDto)) 
      throw new ValidationException("The task object must not be null");
  }

  @Override
  public void validateOnUpdate(Object obj) {

    final TaskDto taskDto = (TaskDto) obj;
    generalValidate(taskDto);

    serviceLog.info("Validating if create user has not changed");
    if (creatorHasChanged.test(taskDto)) 
      throw new ValidationException("The task creator cant be changed");
  }

  @Override
  public void validateOnDelete(Object object) {
    // TODO: para eliminar comprobar que es el mismo usuario
  }

}
