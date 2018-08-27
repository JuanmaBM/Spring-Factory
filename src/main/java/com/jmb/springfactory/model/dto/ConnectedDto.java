package com.jmb.springfactory.model.dto;

import java.util.List;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ConnectedDto {

    private String username;
    private String SessionId;
    private List<String> grantedAuthorities;
    private Integer groupId;
}
