package com.jmb.springfactory.service.worklog;

import org.springframework.beans.factory.annotation.Autowired;

import com.jmb.springfactory.dao.GenericMySQLService;
import com.jmb.springfactory.dao.worklog.WorkLogMySQLService;
import com.jmb.springfactory.model.bo.BusinessObjectBase;
import com.jmb.springfactory.model.dto.WorkLogDto;
import com.jmb.springfactory.model.entity.WorkLog;
import com.jmb.springfactory.service.GenericServiceImpl;

public class WorkLogServiceImpl extends GenericServiceImpl<WorkLog, WorkLogDto, BusinessObjectBase, Integer> 
  implements WorkLogService {

  @Autowired
  private WorkLogMySQLService workLogMySQLService;
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

}
