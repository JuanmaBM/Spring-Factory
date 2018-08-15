package com.jmb.springfactory.service.productionschedule;

import org.springframework.data.domain.Pageable;

import com.jmb.springfactory.model.bo.QueryProductionScheduleObject;
import com.jmb.springfactory.model.dto.PageDto;
import com.jmb.springfactory.model.dto.ProductionScheduleDto;
import com.jmb.springfactory.service.GenericService;

public interface ProductionScheduleService extends GenericService<ProductionScheduleDto, Integer> {

    PageDto<ProductionScheduleDto> findAll(QueryProductionScheduleObject queryObject, Pageable paginator);

}
