package com.wenhui.project.web.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @program: wh_shopbox
 * @description: 用户地址列表查询
 * @author: Mr.Wang
 * @create: 2023-02-12 17:34
 **/
@Data
public class UserAddressListVo {

    @ApiModelProperty("id")
    private Integer id;
    /**
     * 省
     */
    @ApiModelProperty("省")
    private String province;
    @ApiModelProperty("省名称")
    private String provinceName;
    /**
     * 市
     */
    @ApiModelProperty("市")
    private String city;
    @ApiModelProperty("市名称")
    private String cityName;
    /**
     * 区
     */
    @ApiModelProperty("区")
    private String region;
    @ApiModelProperty("区名称")
    private String regionName;
    /**
     * 收货人
     */
    @ApiModelProperty("收货人")
    private String contact;
    /**
     * 手机号码
     */
    @ApiModelProperty("手机号码")
    private String moblie;
    /**
     * 收货地址
     */
    @ApiModelProperty("收货地址")
    private String address;
    /**
     * 是否默认[0、否;1、是]
     */
    @ApiModelProperty("是否默认[0、否;1、是]")
    private Integer isDefault;
}
