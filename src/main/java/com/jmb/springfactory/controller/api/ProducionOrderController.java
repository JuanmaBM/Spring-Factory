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
import com.jmb.springfactory.model.dto.ProductionOrderDTO;
import com.jmb.springfactory.model.enumeration.StatusEnum;
import com.jmb.springfactory.service.UtilsService;
import com.jmb.springfactory.service.productionorder.ProductionOrderService;

import lombok.val;

@RestController
@RequestMapping("/schedule/{idSchedule}/order")
public class ProducionOrderController {

    @Autowired
    private ProductionOrderService productionOrderService;

    @GetMapping
    public List<ProductionOrderDTO> findAll(@PathVariable("idSchedule") Integer idSchedule,
            @RequestParam(value = "groupId", required = false) Integer groupId,
            @RequestParam(value = "status", required = false) String status) throws NotFoundException {

        if (UtilsService.exist(groupId)) {
            val statusEnum = UtilsService.exist(status) ? StatusEnum.valueOf(status) : StatusEnum.OPEN;
            return productionOrderService.findAllByGroup(idSchedule, groupId, statusEnum);
        }

        return productionOrderService.findAllBySchedule(idSchedule);
    }

    @GetMapping("/{idOrder}")
    public ProductionOrderDTO findById(@PathVariable("idSchedule") Integer idSchedule,
            @PathVariable("idOrder") Integer idOrder) throws NotFoundException {
        return productionOrderService.findOne(idOrder);
    }

    @PostMapping
    public ProductionOrderDTO create(@Valid @RequestBody ProductionOrderDTO order,
            @PathVariable("idSchedule") Integer idSchedule) throws ServiceLayerException, NotFoundException {
        return productionOrderService.save(order, idSchedule);
    }

    @PutMapping("/{idOrder}")
    public void update(@Valid @RequestBody ProductionOrderDTO order, @PathVariable("idOrder") Integer idOrder)
            throws ServiceLayerException {
        productionOrderService.update(order, idOrder);
    }

    @DeleteMapping("/{idOrder}")
    public void delete(@PathVariable("idOrder") Integer idOrder) {
        productionOrderService.delete(idOrder);
    }
}
