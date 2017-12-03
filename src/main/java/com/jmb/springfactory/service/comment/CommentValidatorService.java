package com.jmb.springfactory.service.comment;

import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;

import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.jmb.springfactory.dao.comment.CommentMySQLService;
import com.jmb.springfactory.model.dto.CommentDto;
import com.jmb.springfactory.model.dto.UserDto;
import com.jmb.springfactory.model.entity.Comment;
import com.jmb.springfactory.model.entity.User;
import com.jmb.springfactory.service.ValidatorService;

@Service
@Qualifier("commentValidatorService")
public class CommentValidatorService implements ValidatorService {
  
  @Autowired
  private CommentMySQLService commentMySQLService;

  @Override
  public void validateOnCreate(Object object) {
    // Nothing to validate
  }

  @Override
  public void validateOnUpdate(Object object) {
    final CommentDto comment = (CommentDto) object;
    
    if (isTheSameAuthor.test(comment)) {
      throw new ValidationException("The author of comment must be the same who created it");
    }
  }

  @Override
  public void validateOnDelete(Object object) {
    // Nothing to validate
  }

  private Function<CommentDto, Integer> getIdAuthor = comment -> Optional.ofNullable(comment)
      .map(CommentDto::getAuthor)
      .map(UserDto::getId)
      .orElse(null);
  
  private Predicate<CommentDto> isTheSameAuthor = comment -> Optional.ofNullable(comment)
      .map(CommentDto::getId)
      .flatMap(commentMySQLService::findOne)
      .map(Comment::getAuthor)
      .map(User::getId)
      .map(idOriginalAuthor -> idOriginalAuthor.equals(getIdAuthor.apply(comment)))
      .orElse(Boolean.FALSE);
      
}
