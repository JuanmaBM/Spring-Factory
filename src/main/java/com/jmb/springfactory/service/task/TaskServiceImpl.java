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
import com.jmb.springfactory.model.bo.QueryTaskObject;
import com.jmb.springfactory.model.dto.CommentDto;
import com.jmb.springfactory.model.dto.ProductionOrderDTO;
import com.jmb.springfactory.model.dto.TaskDto;
import com.jmb.springfactory.model.dto.WorkGroupDto;
import com.jmb.springfactory.model.dto.WorkLogDto;
import com.jmb.springfactory.model.entity.Task;
import com.jmb.springfactory.model.entity.WorkGroup;
import com.jmb.springfactory.model.enumeration.TaskStatusEnum;
import com.jmb.springfactory.service.GenericServiceImpl;
import com.jmb.springfactory.service.UtilsService;
import com.jmb.springfactory.service.ValidatorService;
import com.jmb.springfactory.service.group.GroupService;

import static com.jmb.springfactory.service.UtilsService.notExist;
import static com.jmb.springfactory.service.UtilsService.addIntoList;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import com.jmb.springfactory.service.productionorder.ProductionOrderService;

import lombok.val;

@Service
public class TaskServiceImpl extends GenericServiceImpl<Task, TaskDto, BusinessObjectBase, Integer>
        implements TaskService {

    @Autowired
    private TaskMySQLService taskMySQLService;

    @Autowired
    private ProductionOrderService productionOrderService;

    @Autowired
    private GroupService groupService;

    @Autowired
    @Qualifier("taskValidatorService")
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
    public TaskDto save(Integer orderId, TaskDto taskDto)
            throws ServiceLayerException, NotFoundException, PersistenceLayerException {

        addInitialInformation(orderId, taskDto);
        return super.save(taskDto);
    }

    /**
     * Add essential information to create a task
     * 
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
     * 
     * @param orderId
     * @return
     * @throws PersistenceLayerException
     */
    private Integer getLastPositionOrder(Integer orderId) throws PersistenceLayerException {

        final Integer numberOrders = taskMySQLService.countByOrderId(orderId).intValue();
        final Integer lastNumberOrder = numberOrders + 1;

        serviceLog.info(String.format("There are %s task for order %s, the order of the new one is %s", orderId,
                numberOrders, lastNumberOrder));

        return lastNumberOrder;
    }

    @Override
    public List<TaskDto> findAll(QueryTaskObject queryParams) {
        serviceLog.info(String.format("Search task with the follow arguments: %s", queryParams.toString()));
        return taskMySQLService.findAll(queryParams).map(this::entityToDto).collect(Collectors.toList());
    }

    @SuppressWarnings("unchecked")
    @Override
    public void addComment(Integer idTask, CommentDto comment) throws NotFoundException, ServiceLayerException {

        final TaskDto task = findOne(idTask);
        task.setComments((List<CommentDto>) addIntoList(task.getComments(), comment));
        save(task);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void addWorkLog(Integer idTask, WorkLogDto workLog) throws NotFoundException, ServiceLayerException {

        final TaskDto task = findOne(idTask);
        task.setWorkLogs((List<WorkLogDto>) addIntoList(task.getWorkLogs(), workLog));
        save(task);
    }

    @Override
    public Optional<TaskDto> findOneById(Integer id) {
        return taskMySQLService.findOne(id).map(this::entityToDto);
    }

    @Override
    public Task merge(TaskDto dto, Task entity) {

        if (UtilsService.existAll(dto, entity)) {
            mergeProductionOrder(dto, entity);
            mergeWorkGroup(dto, entity);
        }

        return super.merge(dto, entity);
    }

    private void mergeWorkGroup(TaskDto dto, Task entity) {
        Optional.ofNullable(entity).map(Task::getGroupAssigned).map(WorkGroup::getId).map(this::findGroupById)
                .ifPresent(dto::setGroupAssigned);
    }

    private void mergeProductionOrder(TaskDto dto, Task entity) {
        ProductionOrderDTO orderDto = null;
        try {
            if (UtilsService.exist(entity.getOrder())) {
                orderDto = productionOrderService.findOne(entity.getOrder().getId());
            }
        } catch (NotFoundException e) {
            serviceLog.warn(e.getMessage());
        }
        dto.setOrder(orderDto);
    }

    private WorkGroupDto findGroupById(Integer id) {
        WorkGroupDto group = null;
        try {
            group = groupService.findOne(id);
        } catch (NotFoundException e) {
            serviceLog.error(e.getMessage());
            group = null;
        }
        return group;
    }

    @Override
    public TaskDto saveByGroup(TaskDto task, Integer groupId) throws NotFoundException, ServiceLayerException {

        if (UtilsService.notExist(task)) {
            serviceLog.error("Task must be present");
            throw new ServiceLayerException("Task must be present");
        }

        serviceLog.info(String.format("Searching group with id %s", groupId));
        final WorkGroupDto groupDto = Optional.ofNullable(groupId).map(this::findGroupById)
                .orElseThrow(NotFoundException::new);

        task.setGroupAssigned(groupDto);
        task.setStatus(TaskStatusEnum.OPENED.name());

        return this.save(task);
    }
}
