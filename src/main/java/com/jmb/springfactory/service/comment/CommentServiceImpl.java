package com.jmb.springfactory.service.comment;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jmb.springfactory.dao.GenericMySQLService;
import com.jmb.springfactory.exceptions.NotFoundException;
import com.jmb.springfactory.exceptions.ServiceLayerException;
import com.jmb.springfactory.model.bo.BusinessObjectBase;
import com.jmb.springfactory.model.dto.CommentDto;
import com.jmb.springfactory.model.dto.TaskDto;
import com.jmb.springfactory.model.entity.Comment;
import com.jmb.springfactory.service.GenericServiceImpl;
import static com.jmb.springfactory.service.UtilsService.exist;
import com.jmb.springfactory.service.task.TaskService;

@Service
public class CommentServiceImpl extends GenericServiceImpl<Comment, CommentDto, BusinessObjectBase, Integer> implements 
  CommentService {

  @Autowired
  private TaskService taskService;
  
  @Autowired
  private CommentValidatorService commentValidatorService;
  
  @Override
  public GenericMySQLService<Comment, Integer> genericDao() {
    return null;
  }

  @Override
  public Class<? extends Comment> getClazz() {
    return Comment.class;
  }

  @Override
  public Class<? extends CommentDto> getDtoClazz() {
    return CommentDto.class;
  }

  @Override
  public Class<? extends BusinessObjectBase> getBoClazz() {
    return BusinessObjectBase.class;
  }
  
  @Override
  public CommentDto save(Integer idTask, CommentDto commentDto) throws NotFoundException, ServiceLayerException {
    
    final CommentDto comment = super.save(commentDto);
    taskService.addComment(idTask, comment);
    
    return comment;
  }
  
  @Override
  public void update(CommentDto commentDto, Integer id) throws ServiceLayerException {
    commentDto.setId(id);
    commentValidatorService.validateOnUpdate(commentDto);
    super.update(commentDto, id);
  }

  @Override
  public CommentDto findOne(Integer idTask, Integer id) throws NotFoundException {
    final TaskDto task = taskService.findOne(idTask);
    final List<CommentDto> taskComments = exist(task.getComments()) ? task.getComments() : new ArrayList<>(); 
    
    return taskComments.parallelStream()
      .filter(comment -> comment.getId().equals(id))
      .findFirst()
      .orElseThrow(NotFoundException::new);
  }

  @Override
  public List<CommentDto> findAll(Integer idTask) throws NotFoundException {
    final TaskDto task = taskService.findOne(idTask);
    return exist(task.getComments()) ? task.getComments() : new ArrayList<>(); 
  }
  
}
