package com.jmb.springfactory.service.user;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.ValidationException;

import static org.apache.commons.lang3.StringUtils.isBlank;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.jmb.springfactory.dao.user.UserMongoService;
import com.jmb.springfactory.model.dto.UserDto;
import com.jmb.springfactory.service.BaseService;
import static com.jmb.springfactory.service.UtilsService.*;
import com.jmb.springfactory.service.ValidatorService;

@Service
@Qualifier("userValidatorService")
public class UserValidatorService extends BaseService implements ValidatorService {

  private static final String PASSWORD_MUST_NOT_BE_EMPTY = "Password mustn't be empty";
  private static final String VALIDATION_ASSIGN_USER_GROUP = "To assigns a user into a group, it must have a role";
  private static final String VALIDATION_DUPLICATED_USER_MESSAGE = "The user %s already exist";
  private static final String PHONE_NUMBER_FIELD = "phoneNumber";
  private static final String SURNAME_FIELD = "surname";
  private static final String NAME_FIELD = "name";
  private static final String NIF_FIELD = "nif";

  @Autowired
  private UserMongoService userMongoService;

  public void validate(UserDto user) {

    serviceLog.info("Validating main fields of user");
    validateIfEmptyUser(user);
    
    serviceLog.info("Validating if user can be assigned into a group");
    validateIfUserCanBeAssignedIntoGroup(user);
  }

  @Override
  public void validateOnCreate(Object object) {
    
    final UserDto user = (UserDto) object;

    serviceLog.info("Validating if user have password");
    validateIfPasswordIsNotEmpty(user);
    
    serviceLog.info("Validating if user already exists");
    validateIfUserAlreadyExist(user);
    
    validate(user);
  }

  private void validateIfPasswordIsNotEmpty(UserDto user) {
    Optional.ofNullable(user)
      .map(UserDto::getPassword)
      .filter(StringUtils::isNotBlank)
      .orElseThrow(() -> new ValidationException(PASSWORD_MUST_NOT_BE_EMPTY));
  }

  /**
   * Check that it's allow assign user into a group
   * @param user
   */
  private void validateIfUserCanBeAssignedIntoGroup(UserDto user) {
    
    final Boolean userCanNotBeAssignedIntoGroup = exist(user.getGroup()) && !exist(user.getRol());
    
    if (userCanNotBeAssignedIntoGroup) {
      serviceLog.error(VALIDATION_ASSIGN_USER_GROUP);
      throw new ValidationException(VALIDATION_ASSIGN_USER_GROUP);
    }
  }

  /**
   * Check that user not exist in BBDD, if exists, then throw validation exception
   * @param user
   */
  private void validateIfUserAlreadyExist(UserDto user) {
    
    final Boolean userAlreadyExists = userMongoService.findByNifContain(user.getNif()).findAny().isPresent();
    
    if (userAlreadyExists) {
      serviceLog.error(String.format(VALIDATION_DUPLICATED_USER_MESSAGE, user.toString()));
      throw new ValidationException(String.format(VALIDATION_DUPLICATED_USER_MESSAGE, user.toString()));
    }
  }

  /**
   * Checks that main fields of user are not empty
   * @param user
   */
  private void validateIfEmptyUser(UserDto user) {
    
    final List<String> emptyFields = new ArrayList<>();
    
    if (isBlank(user.getNif())) {
      emptyFields.add(NIF_FIELD);
    }
    
    if (isBlank(user.getName())) {
      emptyFields.add(NAME_FIELD);
    }
    
    if (isBlank(user.getSurname())) {
      emptyFields.add(SURNAME_FIELD);
    }
    
    if (isBlank(user.getPhoneNumber())) {
      emptyFields.add(PHONE_NUMBER_FIELD);
    }
    
    throwValidationExceptionIfEmptyFieldIsNotEmpty(emptyFields, serviceLog);
  }

  @Override
  public void validateOnUpdate(Object object) {
    final UserDto user = (UserDto) object;
    validate(user);
  }

  @Override
  public void validateOnDelete(Object object) {
    // There are not validation to delete an user
  }

}
