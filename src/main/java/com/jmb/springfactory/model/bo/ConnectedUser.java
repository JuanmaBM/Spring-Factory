package com.jmb.springfactory.model.bo;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ConnectedUser extends User {

    private static final long serialVersionUID = -437685269384982011L;

    private String token;

    public ConnectedUser(String username, String password, Collection<? extends GrantedAuthority> authorities,
            String token) {
        super(username, password, authorities);
    }

}
