package com.wenhui.project.web.vo;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.annotations.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ShippingInfoVo {

    @ApiModelProperty("收件人信息")
    private String consigneeInfo;

    @ApiModelProperty("商品名称")
    private String productName;

    @ApiModelProperty("商品价格")
    private BigDecimal productPrice;

    @ApiModelProperty("购买数量")
    private Integer number;

    @ApiModelProperty("商品主图")
    private String productThumb;

    public JSONArray getProductThumb() {
        JSONArray jsonArray = JSON.parseArray(productThumb);
        return jsonArray;
    }
}
