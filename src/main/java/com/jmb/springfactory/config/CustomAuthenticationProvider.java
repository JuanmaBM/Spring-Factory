package com.jmb.springfactory.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.jmb.springfactory.model.dto.RolDto;
import com.jmb.springfactory.model.dto.UserDto;
import com.jmb.springfactory.service.user.UserService;

import lombok.val;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {
  
  @Autowired
  private UserService userService;

  @Override
  public Authentication authenticate(Authentication authentication) {
    
    val username = Optional.ofNullable(authentication).map(Authentication::getName);
    val password = Optional.ofNullable(authentication).map(Authentication::getCredentials).map(Object::toString);
    
    if (username.isPresent() && password.isPresent()) {
      return validateUserLogin(username.get(), password.get());
    }

    throw new UsernameNotFoundException("Username and password must not be empty");
  }

  private UsernamePasswordAuthenticationToken validateUserLogin(String userName, String password) {
    
    final Predicate<Optional<UserDto>> userPasswordIsTheSameLoginPassword = user ->
      user.map(UserDto::getPassword).map(pass -> pass.equals(password)).orElse(Boolean.FALSE);
    
    val user = userService.findByNif(userName);

    if (userPasswordIsTheSameLoginPassword.test(user)) {

      val encryptPassword = user.map(UserDto::getPassword).orElse(StringUtils.EMPTY);
      final List<SimpleGrantedAuthority> authorities = user.map(UserDto::getRol).map(getGrantedAuthoritiesFromRol)
          .orElse(new ArrayList<>());
        
      return new UsernamePasswordAuthenticationToken(user.get(), encryptPassword, authorities);
    } else {
      throw new UsernameNotFoundException("The credentials introduced is not correct");
    }
  }
  
  final Function<RolDto, List<SimpleGrantedAuthority>> getGrantedAuthoritiesFromRol = rol ->
    rol.getPermissions().parallelStream()
      .map(p -> new SimpleGrantedAuthority(p.getName()))
      .collect(Collectors.toList());
        

  @Override
  public boolean supports(Class<?> authentication) {
    return authentication.equals(UsernamePasswordAuthenticationToken.class);
  }

}
