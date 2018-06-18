package com.jmb.springfactory.controller.api;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jmb.springfactory.controller.GenericController;
import com.jmb.springfactory.model.bo.QueryProductionScheduleObject;
import com.jmb.springfactory.model.dto.ProductionScheduleDto;
import com.jmb.springfactory.model.enumeration.ProductionScheduleStateEnum;
import com.jmb.springfactory.service.GenericService;
import com.jmb.springfactory.service.UtilsService;
import com.jmb.springfactory.service.productionschedule.ProductionScheduleService;

import lombok.val;

@RestController
@RequestMapping("/schedule")
public class ProductionScheduleController extends GenericController<ProductionScheduleDto, Integer> {

    @Autowired
    private ProductionScheduleService productionScheduleService;

    @Override
    public GenericService<ProductionScheduleDto, Integer> genericService() {
        return productionScheduleService;
    }

    @GetMapping
    public List<ProductionScheduleDto> findAll(@RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "state", required = false) String state,
            @RequestParam(value = "estimatedStartDate", required = false) @DateTimeFormat(pattern = "yyy-MM-dd") Date estimatedStartDate,
            @RequestParam(value = "estimatedFinishDate", required = false) @DateTimeFormat(pattern = "yyy-MM-dd") Date estimatedFinishDate,
            @RequestParam(value = "realStartDate", required = false) @DateTimeFormat(pattern = "yyy-MM-dd") Date realStartDate,
            @RequestParam(value = "realFinishDate", required = false) @DateTimeFormat(pattern = "yyy-MM-dd") Date realFinishDate) {

        val stateEnum = UtilsService.exist(state) ? ProductionScheduleStateEnum.valueOf(state) : null;
        val parameterScheduleObject = QueryProductionScheduleObject.builder().estimatedFinishDate(estimatedFinishDate)
                .estimatedStartDate(estimatedStartDate).realFinishDate(realFinishDate).realStartDate(realStartDate)
                .name(name).state(stateEnum).build();

        return productionScheduleService.findAll(parameterScheduleObject);
    }

}
