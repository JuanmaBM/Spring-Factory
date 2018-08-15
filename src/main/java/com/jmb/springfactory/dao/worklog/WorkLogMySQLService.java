package com.jmb.springfactory.dao.worklog;

import java.util.List;

import com.jmb.springfactory.dao.GenericMySQLService;
import com.jmb.springfactory.model.dto.WorkLogDto;
import com.jmb.springfactory.model.entity.WorkLog;

public interface WorkLogMySQLService extends GenericMySQLService<WorkLog, Integer> {

    List<WorkLog> findByTask(Integer idTask);

    List<WorkLog> findByTaskIdAndGroupId(Integer taskId, Integer groupId);

}
