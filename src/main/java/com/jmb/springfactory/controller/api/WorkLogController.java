package com.jmb.springfactory.controller.api;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jmb.springfactory.exceptions.NotFoundException;
import com.jmb.springfactory.exceptions.ServiceLayerException;
import com.jmb.springfactory.model.dto.WorkLogDto;
import static com.jmb.springfactory.service.UtilsService.exist;
import com.jmb.springfactory.service.worklog.WorkLogService;

@RestController
@RequestMapping("/schedule/{idSchedule}/order/{idOrder}/task/{idTask}/worklog")
public class WorkLogController {

    @Autowired
    private WorkLogService workLogService;

    @PostMapping
    public WorkLogDto create(@PathVariable("idTask") Integer idTask, @Valid @RequestBody WorkLogDto dto)
            throws ServiceLayerException, NotFoundException {
        return workLogService.save(idTask, dto);
    }

    @GetMapping("/{id}")
    public WorkLogDto findOne(@PathVariable("id") Integer id) throws NotFoundException {
        return workLogService.findOne(id);
    }

    @GetMapping
    public List<WorkLogDto> findAll(@PathVariable("idTask") Integer idTask,
            @RequestParam(value = "groupId", required = false) Integer groupId) throws NotFoundException {

        final List<WorkLogDto> worklogs;

        if (exist(groupId)) {
            worklogs = workLogService.findByGroupId(idTask, groupId);
        } else {
            worklogs = workLogService.findAll(idTask);
        }
        
        return worklogs;
    }

    @PutMapping("/{id}")
    public void update(@Valid @RequestBody WorkLogDto dto, @PathVariable("id") Integer id)
            throws ServiceLayerException {
        workLogService.update(dto, id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Integer id) {
        workLogService.delete(id);
    }
}
