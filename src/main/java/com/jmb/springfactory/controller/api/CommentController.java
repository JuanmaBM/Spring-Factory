package com.jmb.springfactory.controller.api;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jmb.springfactory.controller.GenericController;
import com.jmb.springfactory.exceptions.NotFoundException;
import com.jmb.springfactory.exceptions.ServiceLayerException;
import com.jmb.springfactory.model.dto.CommentDto;
import com.jmb.springfactory.service.GenericService;
import com.jmb.springfactory.service.comment.CommentService;

@RestController
@RequestMapping("/schedule/{idSchedule}/order/{idOrder}/task/{idTask}/comment")
public class CommentController extends GenericController<CommentDto, Integer> {

  @Autowired
  private CommentService commentService;

  @Override
  public GenericService<CommentDto, Integer> genericService() {
    return commentService;
  }

  @PostMapping
  public CommentDto create(@PathVariable("idTask") Integer idTask, @Valid @RequestBody CommentDto dto) 
      throws ServiceLayerException, NotFoundException {
    return commentService.save(idTask, dto);
  }

  @GetMapping("/{id}")
  public CommentDto findOne(@PathVariable("idTask") Integer idTask, @PathVariable("id") Integer id)
      throws NotFoundException {
    return commentService.findOne(idTask, id);
  }
  
  @GetMapping
  public List<CommentDto> findAll(@PathVariable("idTask") Integer idTask) throws NotFoundException {
    return commentService.findAll(idTask);
  }
}
