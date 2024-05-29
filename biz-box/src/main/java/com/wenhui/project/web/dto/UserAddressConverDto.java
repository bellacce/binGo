package com.wenhui.project.web.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @program: wh_shopbox
 * @description: 地址数据转换
 * @author: Mr.Wang
 * @create: 2023-02-19 17:04
 **/
@Data
@Accessors(chain = true)
public class UserAddressConverDto {

    @ApiModelProperty("id")
    private Integer id;
    /**
     * 用户id
     */
    @NotNull(message = "请输入用户id")
    @ApiModelProperty("用户id")
    private Integer uid;
    @ApiModelProperty("详细收货地址")
    @NotEmpty(message = "详细收货地址不能为空")
    private String addressDetail;

    @ApiModelProperty("收货地址编码")
    @NotEmpty(message = "收货地址编码不能为空")
    private String areaCode;

    @ApiModelProperty("市名称")
    @NotEmpty(message = "市名称不能为空")
    private String city;

    @ApiModelProperty("")
    private String country;

    @ApiModelProperty("区名称")
    @NotEmpty(message = "区名称不能为空")
    private String county;

    @ApiModelProperty("是否默认")
    @NotNull(message = "是否默认不能为空")
    private Boolean isDefault;

    @ApiModelProperty("收货人")
    @NotEmpty(message = "收货人不能为空")
    private String name;

    @ApiModelProperty("")
    private String postalCode;

    @NotEmpty(message = "省名称不能为空")
    @ApiModelProperty("省名称")
    private String province;

    @NotEmpty(message = "手机号码不能为空")
    @ApiModelProperty("手机号码")
    private String tel;

    public UserAddressDto conver() {
        UserAddressDto userAddressDto = new UserAddressDto();
        userAddressDto
                .setUid(this.uid)
                .setId(this.id)
                .setAddress(this.addressDetail)
                .setRegion(this.areaCode)
                .setCity(this.areaCode.substring(0, 4) + "00")
                .setProvince(this.areaCode.substring(0, 2) + "0000")
                .setContact(this.name)
                .setMoblie(this.tel)
                .setIsDefault(this.isDefault ? 1 : 0);
        return userAddressDto;
    }

}
