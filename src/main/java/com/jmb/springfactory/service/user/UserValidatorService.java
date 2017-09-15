package com.jmb.springfactory.service.user;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ValidationException;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.jmb.springfactory.dao.user.UserMongoService;
import com.jmb.springfactory.model.dto.UserDto;
import com.jmb.springfactory.service.BaseService;
import com.jmb.springfactory.service.UtilsService;
import com.jmb.springfactory.service.ValidatorService;

@Service
@Qualifier("userValidatorService")
public class UserValidatorService extends BaseService implements ValidatorService {

  private static final String VALIDATION_DUPLICATED_USER_MESSAGE = "The user %s already exist";
  private static final String PHONE_NUMBER_FIELD = "phoneNumber";
  private static final String SURNAME_FIELD = "surname";
  private static final String NAME_FIELD = "name";
  private static final String NIF_FIELD = "nif";

  @Autowired
  private UserMongoService userMongoService;

  @Override
  public void validate(Object object) {

    final UserDto user = (UserDto) object;

    serviceLog.info("Validating main fields of user");
    validateIfEmptyUser(user);
    
    serviceLog.info("Validating if user already exists");
    validateIfUserAlreadyExist(user);
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
    
    if (StringUtils.isBlank(user.getNif())) {
      emptyFields.add(NIF_FIELD);
    }
    
    if (StringUtils.isBlank(user.getName())) {
      emptyFields.add(NAME_FIELD);
    }
    
    if (StringUtils.isBlank(user.getSurname())) {
      emptyFields.add(SURNAME_FIELD);
    }
    
    if (StringUtils.isBlank(user.getPhoneNumber())) {
      emptyFields.add(PHONE_NUMBER_FIELD);
    }
    
    UtilsService.throwValidationExceptionIfEmptyFieldIsNotEmpty(emptyFields, serviceLog);
  }

}
