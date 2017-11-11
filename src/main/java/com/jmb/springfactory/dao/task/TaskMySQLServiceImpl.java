package com.jmb.springfactory.dao.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.jmb.springfactory.dao.GenericMySQLServiceImpl;
import com.jmb.springfactory.exceptions.PersistenceLayerException;
import com.jmb.springfactory.model.entity.Task;
import static com.jmb.springfactory.service.UtilsService.notExist;

@Service
public class TaskMySQLServiceImpl extends GenericMySQLServiceImpl<Task, Integer> implements TaskMySQLService {

  @Autowired
  private TaskRepository taskRepository;

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
}
