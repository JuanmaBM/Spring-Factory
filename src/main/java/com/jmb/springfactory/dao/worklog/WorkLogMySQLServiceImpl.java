package com.jmb.springfactory.dao.worklog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.jmb.springfactory.dao.GenericMySQLServiceImpl;
import com.jmb.springfactory.model.entity.WorkLog;

@Service
public class WorkLogMySQLServiceImpl extends GenericMySQLServiceImpl<WorkLog, Integer> implements WorkLogMySQLService {

  @Autowired
  private WorkLogRepository workLogRepository;

  @Override
  public JpaRepository<WorkLog, Integer> getRepository() {
    return workLogRepository;
  }

}
