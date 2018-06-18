package com.jmb.springfactory.dao.task;

import static org.apache.commons.lang3.StringUtils.isNotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.GenericPropertyMatcher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.jmb.springfactory.dao.GenericMySQLServiceImpl;
import com.jmb.springfactory.dao.user.UserMongoService;
import com.jmb.springfactory.exceptions.PersistenceLayerException;
import com.jmb.springfactory.model.bo.QueryTaskObject;
import com.jmb.springfactory.model.entity.Task;
import static com.jmb.springfactory.service.UtilsService.*;

import java.util.stream.Stream;

@Service
public class TaskMySQLServiceImpl extends GenericMySQLServiceImpl<Task, Integer> implements TaskMySQLService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserMongoService userMongoService;

    @Override
    public JpaRepository<Task, Integer> getRepository() {
        return taskRepository;
    }

    @Override
    public Long countByOrderId(Integer orderId) throws PersistenceLayerException {

        if (notExist(orderId))
            throw new PersistenceLayerException("To search task by order, the order id must not be null");

        return taskRepository.countByOrder_Id(orderId);
    }

    @Override
    public Stream<Task> findAll(QueryTaskObject queryParams) {

        final ExampleMatcher matcher = ExampleMatcher.matching();
        final Task queryTask = new Task();

        if (isNotBlank(queryParams.getName())) {
            queryTask.setName(queryParams.getName());
            matcher.withMatcher("name", GenericPropertyMatcher::contains);
        }

        if (exist(queryParams.getStatus())) {
            queryTask.setStatus(queryParams.getStatus());
        }

        if (exist(queryParams.getCreator())) {
            userMongoService.findByNameContain(queryParams.getCreator()).findFirst().ifPresent(queryTask::setCreator);
        }

        if (exist(queryParams.getPriority())) {
            queryTask.setPriority(queryParams.getPriority());
        }

        if (exist(queryParams.getStartDate())) {
            queryTask.setStartDate(dateToLocalDate(queryParams.getStartDate()));
        }

        if (exist(queryParams.getFinishDate())) {
            queryTask.setFinishDate(dateToLocalDate(queryParams.getFinishDate()));
        }

        if (exist(queryParams.getOrderId())) {
            queryTask.setOrder(order);
        }

        return taskRepository.findAll(Example.of(queryTask, matcher)).stream();
    }

}
