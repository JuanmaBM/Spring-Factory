package com.jmb.springfactory.dao.task;

import java.util.stream.Stream;

import com.jmb.springfactory.dao.GenericMySQLService;
import com.jmb.springfactory.exceptions.PersistenceLayerException;
import com.jmb.springfactory.model.bo.QueryTaskObject;
import com.jmb.springfactory.model.entity.Task;

public interface TaskMySQLService extends GenericMySQLService<Task, Integer> {

    Long countByOrderId(Integer orderId) throws PersistenceLayerException;

    Stream<Task> findAll(QueryTaskObject queryParams);

    Long countByGroupId(Integer groupId);
}
