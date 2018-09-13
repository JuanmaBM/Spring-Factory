package com.jmb.springfactory.dao.comment;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jmb.springfactory.model.entity.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {

    List<Comment> findByTask_Id(Integer idTask);
    List<Comment> findByTask_IdAndGroup_Id(Integer idTask, Integer groupId);
    Long countByTask_Id(Integer taskId);
    Long countByGroup_Id(Integer groupId);

}
