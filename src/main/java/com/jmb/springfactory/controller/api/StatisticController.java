package com.jmb.springfactory.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jmb.springfactory.model.dto.StatisticDto;
import com.jmb.springfactory.service.statistic.StatisticService;

@RestController
@RequestMapping("/schedule/{scheduleId}/order/{orderId}/statistic")
public class StatisticController {
    
    @Autowired
    private StatisticService statisticService;

    @GetMapping
    public StatisticDto findAll(@PathVariable("scheduleId") Integer scheduleId,
            @PathVariable("orderId") Integer orderId) {
        return statisticService.buildStatisticInformationByOrder(scheduleId, orderId);
    }
}
