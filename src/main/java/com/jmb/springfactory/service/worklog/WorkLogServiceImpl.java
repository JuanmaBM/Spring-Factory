package com.jmb.springfactory.service.worklog;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jmb.springfactory.dao.GenericMySQLService;
import com.jmb.springfactory.dao.task.TaskMySQLService;
import com.jmb.springfactory.dao.worklog.WorkLogMySQLService;
import com.jmb.springfactory.exceptions.NotFoundException;
import com.jmb.springfactory.exceptions.PersistenceLayerException;
import com.jmb.springfactory.exceptions.ServiceLayerException;
import com.jmb.springfactory.model.bo.BusinessObjectBase;
import com.jmb.springfactory.model.dto.TaskDto;
import com.jmb.springfactory.model.dto.WorkLogDto;
import com.jmb.springfactory.model.entity.Task;
import com.jmb.springfactory.model.entity.WorkLog;
import com.jmb.springfactory.service.GenericServiceImpl;
import com.jmb.springfactory.service.UtilsService;
import com.jmb.springfactory.service.task.TaskService;

@Service
public class WorkLogServiceImpl extends GenericServiceImpl<WorkLog, WorkLogDto, BusinessObjectBase, Integer>
        implements WorkLogService {

    @Autowired
    private WorkLogMySQLService workLogMySQLService;

    @Autowired
    private TaskMySQLService taskMySQLService;

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

        final Task task = taskMySQLService.findOne(idTask)
                .orElseThrow(() -> new NotFoundException("The task with id %s not exists"));

        final WorkLog mappedWorklog = Optional.ofNullable(workLog).map(super::dtoToEntity).orElseThrow(
                () -> new ServiceLayerException("A mapping error happened when trying map worklog dto to entity"));
        mappedWorklog.setTask(task);

        final WorkLogDto newWorklog = Optional.ofNullable(mappedWorklog).map(this::saveWorkLog).map(this::entityToDto)
                .orElseThrow(() -> new ServiceLayerException("An error happened when trying create a worklog"));

        UtilsService.logCreatedEntity(newWorklog, serviceLog);

        return newWorklog;
    }

    private WorkLog saveWorkLog(final WorkLog worklog) {
        WorkLog storedWorklog = null;
        try {
            storedWorklog = workLogMySQLService.save(worklog);
        } catch (PersistenceLayerException e) {
            serviceLog.error(e.getMessage());
        }
        return storedWorklog;
    }

    @Override
    public List<WorkLogDto> findAll(Integer idTask) {
        return Optional.ofNullable(idTask).map(workLogMySQLService::findByTask).map(this::convertListEntityToListDto)
                .orElse(Collections.emptyList());
    }

}
