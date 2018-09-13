package com.jmb.springfactory.service.productionschedule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.jmb.springfactory.dao.GenericMySQLService;
import com.jmb.springfactory.dao.productionschedule.ProductionScheduleMySQLService;
import com.jmb.springfactory.exceptions.ServiceLayerException;
import com.jmb.springfactory.model.bo.BusinessObjectBase;
import com.jmb.springfactory.model.bo.QueryProductionScheduleObject;
import com.jmb.springfactory.model.dto.PageDto;
import com.jmb.springfactory.model.dto.ProductionScheduleDto;
import com.jmb.springfactory.model.entity.ProductionSchedule;
import com.jmb.springfactory.model.enumeration.StatusEnum;
import com.jmb.springfactory.service.GenericServiceImpl;
import lombok.val;

@Service
public class ProductionScheduleServiceImpl
        extends GenericServiceImpl<ProductionSchedule, ProductionScheduleDto, BusinessObjectBase, Integer>
        implements ProductionScheduleService {

    @Autowired
    private ProductionScheduleMySQLService productionScheduleMySQLService;

    @Override
    public GenericMySQLService<ProductionSchedule, Integer> genericDao() {
        return productionScheduleMySQLService;
    }

    @Override
    public Class<? extends ProductionSchedule> getClazz() {
        return ProductionSchedule.class;
    }

    @Override
    public Class<? extends ProductionScheduleDto> getDtoClazz() {
        return ProductionScheduleDto.class;
    }

    @Override
    public Class<? extends BusinessObjectBase> getBoClazz() {
        return BusinessObjectBase.class;
    }

    @Override
    public ProductionScheduleDto save(ProductionScheduleDto schedule) throws ServiceLayerException {
        schedule.setState(StatusEnum.OPEN.name());
        return super.save(schedule);
    }

    @Override
    public PageDto<ProductionScheduleDto> findAll(final QueryProductionScheduleObject queryObject,
            final Pageable paginator) {

        final Page<ProductionSchedule> scheduleList;

        if (queryObject.isEmpty()) {
            serviceLog.info("Retrieve all schedules");
            scheduleList = productionScheduleMySQLService.findAll(paginator);
        } else {
            serviceLog.info("Search by parameters");
            scheduleList = findAllByQueryObject(queryObject, paginator);
        }

        return mapPageToPageDto(scheduleList);
    }
    
    private Page<ProductionSchedule> findAllByQueryObject(final QueryProductionScheduleObject queryObject,
            Pageable paginator) {
        val scheduleQuery = getMapper().map(queryObject, getClazz());
        return productionScheduleMySQLService.findAllByExample(scheduleQuery, paginator);
    }

}
