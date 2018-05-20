package com.jmb.springfactory.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import javax.security.sasl.AuthenticationException;

import org.apache.commons.codec.digest.DigestUtils;
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

        final Optional<UserDto> user = userService.findByNif(userName);
        val encryptPassword = Optional.ofNullable(password).map(DigestUtils::sha1Hex).orElse(StringUtils.EMPTY);
        val userPasswordIsTheSameLoginPassword = user.map(UserDto::getPassword).map(encryptPassword::equals)
                .orElse(Boolean.FALSE);

        if (user.isPresent() && userPasswordIsTheSameLoginPassword) {

            final List<SimpleGrantedAuthority> authorities = user.map(UserDto::getRol).map(getGrantedAuthoritiesFromRol)
                    .orElse(new ArrayList<>());
            val userAuthenticated = user.orElseThrow(
                    () -> new UsernameNotFoundException("It cannot extracts the user from the body of request"));

            return new UsernamePasswordAuthenticationToken(userAuthenticated, encryptPassword, authorities);
        } else {
            throw new UsernameNotFoundException("The credentials introduced is not correct");
        }
    }

    final Function<RolDto, List<SimpleGrantedAuthority>> getGrantedAuthoritiesFromRol = rol -> rol.getPermissions()
            .parallelStream().map(p -> new SimpleGrantedAuthority(p.getName())).collect(Collectors.toList());

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }

}
