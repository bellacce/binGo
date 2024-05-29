package com.wenhui.project.web.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @program: wh_shopbox
 * @description: 用户地址Dto
 * @author: Mr.Wang
 * @create: 2023-02-12 17:47
 **/
@Data
@Accessors(chain = true)
public class UserAddressDto {

    @ApiModelProperty("id")
    private Integer id;
    /**
     * 用户id
     */
    @NotNull(message = "请输入用户id")
    @ApiModelProperty("用户id")
    private Integer uid;
    /**
     * 省
     */
    @NotEmpty(message = "省级不能为空")
    @ApiModelProperty("省级编码 传code")
    private String province;
    /**
     * 市
     */
    @NotEmpty(message = "市级不能为空")
    @ApiModelProperty("市级编码 传code")
    private String city;
    /**
     * 区
     */
    @NotEmpty(message = "区级不能为空")
    @ApiModelProperty("区级编码 传code")
    private String region;
    /**
     * 收货人
     */
    @NotEmpty(message = "请输入收货人")
    @ApiModelProperty("收货人")
    private String contact;
    /**
     * 手机号码
     */
    @NotEmpty(message = "请输入手机号码")
    @ApiModelProperty("手机号码")
    private String moblie;
    /**
     * 收货地址
     */
    @NotEmpty(message = "请输入收货地址")
    @ApiModelProperty("收货地址")
    private String address;
    /**
     * 是否默认[0、否;1、是]
     */
    @ApiModelProperty("是否默认[0、否;1、是]")
    private Integer isDefault;
}
