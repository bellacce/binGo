package com.wenhui.project.web.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @program: wh_shopbox
 * @description: 用户地址列表转换查询
 * @author: Mr.Wang
 * @create: 2023-02-19 16:40
 **/
@Data
public class UserAddressConverListVo {

    @ApiModelProperty("详细收货地址")
    private String addressDetail;
    @ApiModelProperty("收货地址编码")
    private String areaCode;
    @ApiModelProperty("市名称")
    private String city;
    @ApiModelProperty("")
    private String country;
    @ApiModelProperty("区名称")
    private String county;
    @ApiModelProperty("是否默认")
    private Boolean isDefault;
    @ApiModelProperty("收货人")
    private String name;
    @ApiModelProperty("")
    private String postalCode;
    @ApiModelProperty("省名称")
    private String province;
    @ApiModelProperty("手机号码")
    private String tel;

    public UserAddressConverListVo(UserAddressListVo userAddressListVo) {
        this.addressDetail = userAddressListVo.getAddress();
        this.areaCode = userAddressListVo.getRegion();
        this.city = userAddressListVo.getCityName();
        this.county = userAddressListVo.getRegionName();
        this.isDefault = userAddressListVo.getIsDefault() == 0 ? false : true;
        this.name = userAddressListVo.getContact();
        this.province = userAddressListVo.getProvinceName();
        this.tel = userAddressListVo.getMoblie();
    }
}
