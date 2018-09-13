package com.jmb.springfactory.service.statistic;

import com.jmb.springfactory.model.dto.StatisticDto;

public interface StatisticService {

    StatisticDto buildStatisticInformationByOrder(Integer scheduleId, Integer orderId);

}
