package com.jmb.springfactory.dao.task;

import com.jmb.springfactory.dao.GenericMySQLService;
import com.jmb.springfactory.exceptions.PersistenceLayerException;
import com.jmb.springfactory.model.entity.Task;

public interface TaskMySQLService extends GenericMySQLService<Task, Integer> {

  public Long countByOrderId(Integer orderId) throws PersistenceLayerException;
}
