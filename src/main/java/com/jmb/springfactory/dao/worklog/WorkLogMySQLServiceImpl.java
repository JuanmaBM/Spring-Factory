package com.jmb.springfactory.dao.worklog;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.jmb.springfactory.dao.GenericMySQLServiceImpl;
import com.jmb.springfactory.model.dto.WorkLogDto;
import com.jmb.springfactory.model.entity.WorkLog;

@Service
public class WorkLogMySQLServiceImpl extends GenericMySQLServiceImpl<WorkLog, Integer> implements WorkLogMySQLService {

    @Autowired
    private WorkLogRepository workLogRepository;

    @Override
    public JpaRepository<WorkLog, Integer> getRepository() {
        return workLogRepository;
    }

    @Override
    public List<WorkLog> findByTask(final Integer idTask) {
        return workLogRepository.findByTask_Id(idTask);
    }

    @Override
    public List<WorkLog> findByTaskIdAndGroupId(Integer taskId, Integer groupId) {
        return workLogRepository.findByTask_IdAndGroup_Id(taskId, groupId);
    }

}
