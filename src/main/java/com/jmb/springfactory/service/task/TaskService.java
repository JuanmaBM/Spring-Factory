package com.jmb.springfactory.service.task;

import java.util.List;
import java.util.Optional;

import com.jmb.springfactory.exceptions.NotFoundException;
import com.jmb.springfactory.exceptions.PersistenceLayerException;
import com.jmb.springfactory.exceptions.ServiceLayerException;
import com.jmb.springfactory.model.bo.QueryTaskObject;
import com.jmb.springfactory.model.dto.CommentDto;
import com.jmb.springfactory.model.dto.TaskDto;
import com.jmb.springfactory.model.dto.WorkLogDto;
import com.jmb.springfactory.service.GenericService;

public interface TaskService extends GenericService<TaskDto, Integer> {

    public TaskDto save(Integer orderId, TaskDto taskDto)
            throws ServiceLayerException, NotFoundException, PersistenceLayerException;

    public List<TaskDto> findAll(QueryTaskObject queryParams);

    public void addComment(Integer idTask, CommentDto comment) throws NotFoundException, ServiceLayerException;

    public void addWorkLog(Integer idTask, WorkLogDto workLog) throws NotFoundException, ServiceLayerException;

    public Optional<TaskDto> findOneById(Integer id);

    public TaskDto saveByGroup(TaskDto task, Integer groupId) throws NotFoundException, ServiceLayerException;
}
