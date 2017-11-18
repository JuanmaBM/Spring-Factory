package com.jmb.springfactory.service.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.jmb.springfactory.dao.GenericMySQLService;
import com.jmb.springfactory.dao.task.TaskMySQLService;
import com.jmb.springfactory.exceptions.NotFoundException;
import com.jmb.springfactory.exceptions.PersistenceLayerException;
import com.jmb.springfactory.exceptions.ServiceLayerException;
import com.jmb.springfactory.model.bo.BusinessObjectBase;
import com.jmb.springfactory.model.dto.TaskDto;
import com.jmb.springfactory.model.entity.Task;
import com.jmb.springfactory.model.enumeration.TaskStatusEnum;
import com.jmb.springfactory.service.GenericServiceImpl;
import com.jmb.springfactory.service.ValidatorService;

import static com.jmb.springfactory.service.UtilsService.notExist;
import com.jmb.springfactory.service.productionorder.ProductionOrderService;

@Service
public class TaskServiceImpl extends GenericServiceImpl<Task, TaskDto, BusinessObjectBase, Integer> 
  implements TaskService {

  @Autowired
  private TaskMySQLService taskMySQLService;
  
  @Autowired
  private ProductionOrderService productionOrderService;
  
  @Autowired
  @Qualifier("TaskValidatorService")
  private ValidatorService taskValidatorService;

  private static final String TASK_INITIAL_STATUS = TaskStatusEnum.OPENED.name();

  @Override
  public GenericMySQLService<Task, Integer> genericDao() {
    return taskMySQLService;
  }

  @Override
  public Class<? extends Task> getClazz() {
    return Task.class;
  }

  @Override
  public Class<? extends TaskDto> getDtoClazz() {
    return TaskDto.class;
  }

  @Override
  public Class<? extends BusinessObjectBase> getBoClazz() {
    return BusinessObjectBase.class;
  }
  
  @Override
  public void update(TaskDto taskDto, Integer idTask) throws ServiceLayerException {
    taskValidatorService.validateOnUpdate(taskDto);
    super.update(taskDto, idTask);
  }
  
  @Override
  public TaskDto save(Integer orderId, TaskDto taskDto) throws ServiceLayerException, NotFoundException, 
    PersistenceLayerException {
    
    addInitialInformation(orderId, taskDto);
    return super.save(taskDto);
  }
  
  @Override 
  public void delete(Integer id) {
    throw new UnsupportedOperationException();
  }

  /**
   * Add essential information to create a task
   * @param orderId
   * @param taskDto
   * @throws NotFoundException
   * @throws PersistenceLayerException
   */
  private void addInitialInformation(Integer orderId, TaskDto taskDto)
      throws NotFoundException, PersistenceLayerException {

    serviceLog.info(String.format("Adding task into order %s", orderId));
    taskDto.setOrder(productionOrderService.findOne(orderId));
    
    if (notExist(taskDto.getOrderNumber())) {
      serviceLog.info("The order number is not defined");
      taskDto.setOrderNumber(getLastPositionOrder(orderId));
    }

    serviceLog.info(String.format("Set task initial status as %s", TASK_INITIAL_STATUS));
    taskDto.setStatus(TASK_INITIAL_STATUS);
  }

  /**
   * Get the last position order of all task of specific order
   * @param orderId
   * @return
   * @throws PersistenceLayerException
   */
  private Integer getLastPositionOrder(Integer orderId) throws PersistenceLayerException {

    final Integer numberOrders = taskMySQLService.countByOrderId(orderId).intValue();
    final Integer lastNumberOrder = numberOrders + 1;

    serviceLog.info(String.format("There are %s task for order %s, the order of the new one is %s", 
        orderId, numberOrders, lastNumberOrder));

    return lastNumberOrder;
  }

}
