package com.jmb.springfactory.service.comment;

import com.jmb.springfactory.exceptions.NotFoundException;
import com.jmb.springfactory.exceptions.ServiceLayerException;
import com.jmb.springfactory.model.dto.CommentDto;

public interface CommentService {

  public CommentDto save(Integer idTask, CommentDto commentDto) throws NotFoundException, ServiceLayerException;
}
