package com.jmb.springfactory.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jmb.springfactory.controller.GenericController;
import com.jmb.springfactory.model.dto.WorkLogDto;
import com.jmb.springfactory.service.GenericService;
import com.jmb.springfactory.service.worklog.WorkLogService;

@RestController
@RequestMapping("/schedule/{idSchedule}/order/{idOrder}/task/{idTask}/worklog")
public class WorkLogController extends GenericController<WorkLogDto, Integer> {

  @Autowired
  private WorkLogService workLogService;

  @Override
  public GenericService<WorkLogDto, Integer> genericService() {
    return workLogService;
  }

  
}
