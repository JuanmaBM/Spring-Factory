package com.jmb.springfactory.controller.api;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jmb.springfactory.exceptions.NotFoundException;
import com.jmb.springfactory.exceptions.ServiceLayerException;
import com.jmb.springfactory.model.bo.QueryTaskObject;
import com.jmb.springfactory.model.dto.TaskDto;
import com.jmb.springfactory.service.task.TaskService;

@RestController
@RequestMapping("/group/{groupId}/task")
public class GroupTaskController {

    @Autowired
    private TaskService taskService;

    @PostMapping
    public TaskDto create(@Valid @RequestBody TaskDto task, @PathVariable("groupId") Integer groupId)
            throws NotFoundException, ServiceLayerException {
        return taskService.saveByGroup(task, groupId);
    }
    
    @DeleteMapping("/{taskId}")
    public void delete(@PathVariable("taskId") Integer taskId) {
        taskService.delete(taskId);
    }
    
    @GetMapping
    public List<TaskDto> findAll(@PathVariable("groupId") Integer groupId) {
        final QueryTaskObject queryTask = QueryTaskObject.builder().groupId(groupId).build();
        return taskService.findAll(queryTask);
    }
}
