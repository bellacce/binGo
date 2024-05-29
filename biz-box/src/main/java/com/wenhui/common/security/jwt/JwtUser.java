package com.wenhui.common.security.jwt;

import lombok.Data;

import java.security.Principal;

/*@Data
@Builder*/
@Data
public class JwtUser implements Principal {

    private String openId;

    private Long userId;

    private String mobile;

    private String userName;

    private String userPassword;

    @Override
    public String getName() {
        return this.userName;
    }
}
