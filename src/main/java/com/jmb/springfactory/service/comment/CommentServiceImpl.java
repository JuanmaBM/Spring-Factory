package com.jmb.springfactory.service.comment;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.jmb.springfactory.dao.GenericMySQLService;
import com.jmb.springfactory.dao.comment.CommentMySQLService;
import com.jmb.springfactory.dao.group.GroupMongoService;
import com.jmb.springfactory.dao.task.TaskMySQLService;
import com.jmb.springfactory.dao.user.UserMongoService;
import com.jmb.springfactory.exceptions.NotFoundException;
import com.jmb.springfactory.exceptions.PersistenceLayerException;
import com.jmb.springfactory.exceptions.ServiceLayerException;
import com.jmb.springfactory.model.bo.BusinessObjectBase;
import com.jmb.springfactory.model.dto.CommentDto;
import com.jmb.springfactory.model.dto.UserDto;
import com.jmb.springfactory.model.dto.WorkGroupDto;
import com.jmb.springfactory.model.dto.WorkLogDto;
import com.jmb.springfactory.model.entity.Comment;
import com.jmb.springfactory.model.entity.Task;
import com.jmb.springfactory.model.entity.User;
import com.jmb.springfactory.model.entity.WorkGroup;
import com.jmb.springfactory.service.GenericServiceImpl;
import com.jmb.springfactory.service.ValidatorService;

@Service
public class CommentServiceImpl extends GenericServiceImpl<Comment, CommentDto, BusinessObjectBase, Integer>
        implements CommentService {

    @Autowired
    private TaskMySQLService taskMySQLService;

    @Autowired
    private CommentMySQLService commentMySQLService;

    @Autowired
    private UserMongoService userMongoService;

    @Autowired
    @Qualifier("commentValidatorService")
    private ValidatorService commentValidatorService;

    @Override
    public GenericMySQLService<Comment, Integer> genericDao() {
        return commentMySQLService;
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

        final Task task = taskMySQLService.findOne(idTask)
                .orElseThrow(() -> new NotFoundException("The task with id %s not exists"));

        final Comment comment = Optional.ofNullable(commentDto).map(super::dtoToEntity).orElseThrow(
                () -> new ServiceLayerException("A mapping error happened when trying map comment dto to entity"));

        final User user = Optional.ofNullable(commentDto).map(CommentDto::getAuthor).map(UserDto::getNif)
                .flatMap(userMongoService::findByNif)
                .orElseThrow(() -> new ServiceLayerException("The author of comment not exists"));

        comment.setGroup(user.getGroup());
        comment.setAuthor(user);
        comment.setTask(task);

        return Optional.ofNullable(comment).map(this::saveComment).map(super::entityToDto)
                .orElseThrow(() -> new ServiceLayerException("An error happened when trying create a comment"));
    }

    private Comment saveComment(final Comment comment) {
        Comment storedComment = null;
        try {
            storedComment = commentMySQLService.save(comment);
        } catch (PersistenceLayerException e) {
            serviceLog.error(e.getMessage());
        }
        return storedComment;
    }

    @Override
    public void update(CommentDto commentDto, Integer id) throws ServiceLayerException {
        commentDto.setId(id);
        commentValidatorService.validateOnUpdate(commentDto);
        super.update(commentDto, id);
    }

    @Override
    public List<CommentDto> findAll(Integer idTask) throws NotFoundException {
        return Optional.ofNullable(idTask).map(commentMySQLService::findByTask).map(super::convertListEntityToListDto)
                .orElse(Collections.emptyList());
    }

    @Override
    public List<CommentDto> findByGroupId(Integer idTask, Integer groupId) {
        return commentMySQLService.findByTaskAndGroup(idTask, groupId).stream().map(this::entityToDto)
                .collect(Collectors.toList());
    }
    
    @Override
    public Long countByTask(final Integer taskId) {
        return Optional.ofNullable(taskId).map(commentMySQLService::countByTask).orElse(0L);
    }

}
