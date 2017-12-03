package com.jmb.springfactory.service.worklog;

import java.util.List;

import com.jmb.springfactory.exceptions.NotFoundException;
import com.jmb.springfactory.exceptions.ServiceLayerException;
import com.jmb.springfactory.model.dto.WorkLogDto;
import com.jmb.springfactory.service.GenericService;

public interface WorkLogService extends GenericService<WorkLogDto, Integer> {

  public WorkLogDto save(Integer idTask, WorkLogDto workLog) throws NotFoundException, ServiceLayerException;
  public WorkLogDto findOne(Integer idTask, Integer idWorkLog) throws NotFoundException;
  public List<WorkLogDto> findAll(Integer idTask);
}
