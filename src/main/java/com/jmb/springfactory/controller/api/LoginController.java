package com.jmb.springfactory.controller.api;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;

import com.jmb.springfactory.model.dto.ConnectedDto;
import com.jmb.springfactory.model.dto.PermisionDto;
import com.jmb.springfactory.model.enumeration.PermissionsEnum;
import com.jmb.springfactory.service.user.UserService;

@RestController
@RequestMapping("/api")
public class LoginController {

    private static final String DEFAULT_ADMIN_PASSWORD = "21232f297a57a5a743894a0e4a801fc3";

    private static final String DEFAULT_ADMIN_NAME = "admin";

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/login", method = { RequestMethod.POST })
    public ConnectedDto login(@RequestHeader("username") String username, @RequestHeader("password") String password) {

        if (DEFAULT_ADMIN_NAME.equalsIgnoreCase(username) && DEFAULT_ADMIN_PASSWORD.equalsIgnoreCase(password)) {
            final List<String> adminPermissions = Arrays.asList(PermissionsEnum.values()).stream()
                    .map(PermissionsEnum::name).collect(Collectors.toList());
            return ConnectedDto.builder().username(username).grantedAuthorities(adminPermissions)
                    .build();
        }

        final Authentication token = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(username, password));
        final String sessionId = RequestContextHolder.currentRequestAttributes().getSessionId();
        final List<String> grantedAuthorities = getPermissions(username);
        final Integer groupId = userService.getGroupId(username);

        return ConnectedDto.builder().username(username).grantedAuthorities(grantedAuthorities).SessionId(sessionId)
                .groupId(groupId).build();
    }

    @GetMapping("/connection")
    public ResponseEntity<ConnectedDto> connected(Authentication auth) {

        final Boolean isConected = Optional.ofNullable(auth).map(Authentication::getPrincipal).isPresent();
        final HttpStatus statusConnection = isConected ? HttpStatus.OK : HttpStatus.UNAUTHORIZED;

        return new ResponseEntity<>(statusConnection);
    }

    private List<String> getPermissions(String username) {
        return userService.getPermission(username).stream().map(PermisionDto::getName).collect(Collectors.toList());
    }
}
