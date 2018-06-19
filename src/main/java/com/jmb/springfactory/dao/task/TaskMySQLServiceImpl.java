package com.jmb.springfactory.dao.task;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

import org.apache.commons.lang3.StringUtils;
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
import com.jmb.springfactory.model.entity.ProductionOrder;
import com.jmb.springfactory.model.entity.ProductionSchedule;
import com.jmb.springfactory.model.entity.Task;
import com.jmb.springfactory.service.UtilsService;

import lombok.val;

import static com.jmb.springfactory.service.UtilsService.*;

import java.util.Optional;
import java.util.function.Function;
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
        
        final Function<Integer, ProductionOrder> buildOrderWithId = orderId -> {
            val order = new ProductionOrder();
            order.setId(queryParams.getOrderId());
            return order;
        };

        final Optional<QueryTaskObject> params = Optional.ofNullable(queryParams);
        final ExampleMatcher matcher = ExampleMatcher.matching();
        final Task queryTask = new Task();

        if (exist(queryParams.getCreator())) {
            userMongoService.findByNameContain(queryParams.getCreator()).findFirst().ifPresent(queryTask::setCreator);
        }

        params.map(QueryTaskObject::getStatus).ifPresent(queryTask::setStatus);
        params.map(QueryTaskObject::getPriority).ifPresent(queryTask::setPriority);
        params.map(QueryTaskObject::getStartDate).map(UtilsService::dateToLocalDate).ifPresent(queryTask::setStartDate);
        params.map(QueryTaskObject::getFinishDate).map(UtilsService::dateToLocalDate).ifPresent(queryTask::setFinishDate);
        params.map(QueryTaskObject::getOrderId).map(buildOrderWithId).ifPresent(queryTask::setOrder);

        params.map(QueryTaskObject::getName).filter(StringUtils::isNotBlank).ifPresent(name -> {
            queryTask.setName(name);
            matcher.withMatcher("name", GenericPropertyMatcher::contains);
        });
        
        return taskRepository.findAll(Example.of(queryTask, matcher)).stream();
    }

}
