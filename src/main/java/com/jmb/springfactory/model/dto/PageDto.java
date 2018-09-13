package com.jmb.springfactory.model.dto;

import java.util.List;

import lombok.Data;

@Data
public class PageDto<T extends BaseDto> {
    private List<T> list;
    private Integer page;
    private Integer totalPages;
}
