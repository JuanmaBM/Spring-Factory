package com.jmb.springfactory.service.group;

import java.util.List;

import com.jmb.springfactory.model.dto.WorkGroupDto;
import com.jmb.springfactory.service.GenericService;

public interface GroupService extends GenericService<WorkGroupDto, Integer> {

    List<WorkGroupDto> findAllByOrderId(Integer orderId);

}
