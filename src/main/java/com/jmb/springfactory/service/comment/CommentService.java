package com.jmb.springfactory.service.comment;

import java.util.List;

import com.jmb.springfactory.exceptions.NotFoundException;
import com.jmb.springfactory.exceptions.ServiceLayerException;
import com.jmb.springfactory.model.dto.CommentDto;
import com.jmb.springfactory.service.GenericService;

public interface CommentService extends GenericService<CommentDto, Integer> {

    public CommentDto save(Integer idTask, CommentDto commentDto) throws NotFoundException, ServiceLayerException;

    public List<CommentDto> findAll(Integer idTask) throws NotFoundException;

    public List<CommentDto> findByGroupId(Integer idTask, Integer groupId);
}
