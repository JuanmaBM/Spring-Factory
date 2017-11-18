package com.jmb.springfactory.controller.api;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jmb.springfactory.exceptions.NotFoundException;
import com.jmb.springfactory.exceptions.PersistenceLayerException;
import com.jmb.springfactory.exceptions.ServiceLayerException;
import com.jmb.springfactory.model.dto.TaskDto;
import com.jmb.springfactory.service.task.TaskService;

@RestController
@RequestMapping("/schedule/{idSchedule}/order/{idOrder}/task")
public class TaskController {

  @Autowired
  private TaskService taskService;
  
  @DeleteMapping("/{id}")
  public void delete(@PathVariable("id") Integer id) {
    throw new UnsupportedOperationException();
  }

  @PostMapping
  public TaskDto create(@Valid @RequestBody TaskDto task, @PathVariable("idOrder") Integer orderId) 
      throws ServiceLayerException, NotFoundException, PersistenceLayerException {
    return taskService.save(orderId, task);
  }
  
  @PutMapping
  public void update(@Valid @RequestBody TaskDto task, @PathVariable("idOrder") Integer orderId,
     @PathVariable("id") Integer taskId) throws ServiceLayerException {
    taskService.update(task, taskId);
  }
}
