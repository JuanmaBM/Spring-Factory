package com.jmb.springfactory.controller;

import java.io.Serializable;
import java.util.List;
import javax.validation.Valid;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.jmb.springfactory.exceptions.NotFoundException;
import com.jmb.springfactory.exceptions.ServiceLayerException;
import com.jmb.springfactory.model.dto.BaseDto;
import com.jmb.springfactory.service.GenericService;
import com.jmb.springfactory.service.UtilsService;

import lombok.val;

public abstract class GenericController<D extends BaseDto, I extends Serializable> extends BaseController {

    private static final int DEFAULT_SIZE_RESULTS = 50;
    private static final int DEFAULT_PAGE = 0;

    public abstract GenericService<D, I> genericService();

    @PostMapping
    public D create(@Valid @RequestBody D dto) throws ServiceLayerException {
        return genericService().save(dto);
    }

    @PutMapping("/{id}")
    public void update(@Valid @RequestBody D dto, @PathVariable("id") I id) throws ServiceLayerException {
        genericService().update(dto, id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") I id) {
        genericService().delete(id);
    }

    @GetMapping("/{id}")
    public D findOne(@PathVariable("id") I id) throws NotFoundException {
        return genericService().findOne(id);
    }

    public Pageable createDefaultPaginator() {
        return createPaginator(DEFAULT_PAGE, DEFAULT_SIZE_RESULTS);
    }

    public Pageable createPaginator(final Integer page, final Integer size) {
        return createPaginator(page, size, null);
    }

    public Pageable createPaginator(final Integer page, final Integer size, final List<String> sortList) {

        val pageValue = UtilsService.exist(page) ? page : DEFAULT_PAGE;
        val sizeValue = UtilsService.exist(size) ? size : DEFAULT_SIZE_RESULTS;

        final Sort queryOrder = createSort(sortList);

        return new PageRequest(pageValue, sizeValue, queryOrder);
    }

    private Sort createSort(final List<String> sortList) {
        final String[] order = CollectionUtils.isNotEmpty(sortList) ? sortList.toArray(new String[0]) : null;
        return UtilsService.exist(order) ? new Sort(order) : null;
    }

}
