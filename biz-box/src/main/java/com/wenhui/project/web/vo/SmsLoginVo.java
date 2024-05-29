package com.wenhui.project.web.vo;

import lombok.Data;

import java.util.Date;

/**
 * @program: wh_shopbox
 * @description: 登录响应vo
 * @author: Mr.Wang
 * @create: 2023-02-12 15:46
 **/
@Data
public class SmsLoginVo {
    private Long userId;
    private String token;
    private String userName;
    private Date createdAt;
}
