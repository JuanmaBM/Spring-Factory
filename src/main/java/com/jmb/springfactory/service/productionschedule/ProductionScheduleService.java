package com.jmb.springfactory.service.productionschedule;

import java.util.List;

import com.jmb.springfactory.model.bo.QueryProductionScheduleObject;
import com.jmb.springfactory.model.dto.ProductionScheduleDto;
import com.jmb.springfactory.service.GenericService;

public interface ProductionScheduleService extends GenericService<ProductionScheduleDto, Integer> {

    List<ProductionScheduleDto> findAll(QueryProductionScheduleObject queryObject);

}
