package com.jmb.springfactory.dao.comment;

import java.util.List;

import com.jmb.springfactory.dao.GenericMySQLService;
import com.jmb.springfactory.model.entity.Comment;

public interface CommentMySQLService extends GenericMySQLService<Comment, Integer> {

    List<Comment> findByTask(Integer idTask);

    List<Comment> findByTaskAndGroup(Integer idTask, Integer groupId);

    Long countByTask(Integer taskId);

    Long countByGroup(Integer groupId);

}
