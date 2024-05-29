package com.wenhui.project.web.dto;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.wenhui.project.web.vo.UserAddressListVo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Data
public class PickupGoodsDto {

    /**
     * 商品名称
     */
    @ApiModelProperty("商品名称")
    private String productName;

    /**
     * 商品主图
     */
    @ApiModelProperty("商品主图")
    private String productThumb;


    @ApiModelProperty("用户地址")
    UserAddressListVo userAddress;

    public JSONArray getProductThumb() {
        JSONArray jsonArray = JSON.parseArray(productThumb);
        return jsonArray;
    }

}
