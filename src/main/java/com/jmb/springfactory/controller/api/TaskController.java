package com.jmb.springfactory.controller.api;

import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static com.jmb.springfactory.service.UtilsService.*;
import com.jmb.springfactory.exceptions.NotFoundException;
import com.jmb.springfactory.exceptions.PersistenceLayerException;
import com.jmb.springfactory.exceptions.ServiceLayerException;
import com.jmb.springfactory.model.bo.QueryTaskObject;
import com.jmb.springfactory.model.dto.TaskDto;
import com.jmb.springfactory.model.enumeration.PriorityEnum;
import com.jmb.springfactory.model.enumeration.TaskStatusEnum;
import com.jmb.springfactory.service.task.TaskService;

@RestController
@RequestMapping("/schedule/{idSchedule}/order/{idOrder}/task")
public class TaskController {

  @Autowired
  private TaskService taskService;
  
  @DeleteMapping("/{id}")
  public void delete(@PathVariable("id") Integer id) {
    taskService.delete(id);
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
  
  @GetMapping("/{id}")
  public TaskDto findOne(@PathVariable("id") Integer taskId) throws NotFoundException {
    return taskService.findOne(taskId);
  }
  
  @GetMapping
  public List<TaskDto> findAll(@RequestParam(value = "name", required = false) String name,
      @RequestParam(value = "status", required = false) String status,
      @RequestParam(value = "startDate", required = false) Date startDate,
      @RequestParam(value = "finishDate", required = false) Date finishDate,
      @RequestParam(value = "priority", required = false) String priority,
      @RequestParam(value = "creator", required = false) String creator) {
    
    final TaskStatusEnum taskStatus = exist(status) ? TaskStatusEnum.valueOf(status) : null;
    final PriorityEnum taskPriority = exist(priority) ? PriorityEnum.valueOf(priority) : null;

    final QueryTaskObject queryParams = QueryTaskObject.builder().name(name).startDate(startDate).finishDate(finishDate)
        .creator(creator).priority(taskPriority).status(taskStatus).build();

    return taskService.findAll(queryParams);
  }
}
