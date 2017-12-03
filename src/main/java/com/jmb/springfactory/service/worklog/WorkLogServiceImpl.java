package com.jmb.springfactory.service.worklog;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jmb.springfactory.dao.GenericMySQLService;
import com.jmb.springfactory.dao.worklog.WorkLogMySQLService;
import com.jmb.springfactory.exceptions.NotFoundException;
import com.jmb.springfactory.exceptions.ServiceLayerException;
import com.jmb.springfactory.model.bo.BusinessObjectBase;
import com.jmb.springfactory.model.dto.TaskDto;
import com.jmb.springfactory.model.dto.WorkLogDto;
import com.jmb.springfactory.model.entity.WorkLog;
import com.jmb.springfactory.service.GenericServiceImpl;
import com.jmb.springfactory.service.task.TaskService;

@Service
public class WorkLogServiceImpl extends GenericServiceImpl<WorkLog, WorkLogDto, BusinessObjectBase, Integer> 
  implements WorkLogService {

  @Autowired
  private WorkLogMySQLService workLogMySQLService;
  
  @Autowired
  private TaskService taskService;

  private Function<Integer, Optional<List<WorkLogDto>>> getWorkLogFromTask = idTask -> Optional.ofNullable(idTask)
      .flatMap(taskService::findOneById)
      .map(TaskDto::getWorkLogs);

  @Override
  public GenericMySQLService<WorkLog, Integer> genericDao() {
    return workLogMySQLService;
  }

  @Override
  public Class<? extends WorkLog> getClazz() {
    return WorkLog.class;
  }

  @Override
  public Class<? extends WorkLogDto> getDtoClazz() {
    return WorkLogDto.class;
  }

  @Override
  public Class<? extends BusinessObjectBase> getBoClazz() {
    return BusinessObjectBase.class;
  }
  
  @Override
  public WorkLogDto save(Integer idTask, WorkLogDto workLog) throws NotFoundException, ServiceLayerException {
    
    final WorkLogDto newWorkLog = super.save(workLog);
    taskService.addWorkLog(idTask, newWorkLog);
    
    return newWorkLog;
  }
  
  @Override
  public WorkLogDto findOne(Integer idTask, Integer idWorkLog) throws NotFoundException{
    
    final Function<List<WorkLogDto>, Optional<WorkLogDto>> filterWorkLogById = workLogs -> 
        workLogs.parallelStream().filter(workLog -> workLog.getId().equals(idWorkLog)).findFirst();

    return getWorkLogFromTask.apply(idTask) 
        .flatMap(filterWorkLogById)
        .orElseThrow(NotFoundException::new);
  }
  
  @Override
  public List<WorkLogDto> findAll(Integer idTask) {
    return getWorkLogFromTask.apply(idTask)
        .orElse(new ArrayList<>());
  }
  
}
