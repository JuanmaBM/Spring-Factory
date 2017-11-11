package com.jmb.springfactory.dao.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;

import com.jmb.springfactory.dao.GenericMySQLServiceImpl;
import com.jmb.springfactory.model.entity.Task;

public class TaskMySQLServiceImpl extends GenericMySQLServiceImpl<Task, Integer> implements TaskMySQLService {

  @Autowired
  private TaskRepository taskRepository;

  @Override
  public JpaRepository<Task, Integer> getRepository() {
    return taskRepository;
  }

}
