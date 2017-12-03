package com.jmb.springfactory.dao.comment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jmb.springfactory.model.entity.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {

}
