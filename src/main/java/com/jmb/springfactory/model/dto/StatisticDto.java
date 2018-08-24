package com.jmb.springfactory.model.dto;

import java.util.Map;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StatisticDto {

    private Map<String, Long> priority;
    private Map<String, Long> status;
    private Map<String, Long> estimatedHours;
    private Map<String, Long> workedHours;
    private Map<String, Long> taskByGroup;
    private Map<String, Long> workedHoursByGroup;
    private Map<String, Long> commentByGroup;
    private Map<String, Long> commentByTask;
    private Long totalEstimatedTime;
    private Long totalWorkedHours;
}
