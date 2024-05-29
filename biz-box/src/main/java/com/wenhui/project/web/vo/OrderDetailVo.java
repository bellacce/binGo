package com.wenhui.project.web.vo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @program: wh_shopbox
 * @description: 商品vo
 * @author: Mr.Wang
 * @create: 2023-02-21 00:15
 **/
@Data
public class OrderDetailVo {
    /**
     * 商品名称
     */
    @ApiModelProperty("商品名称")
    private String name;

    public JSONArray getThumb() {
        JSONArray jsonArray = JSON.parseArray(thumb);
        return jsonArray;
    }

    /**
     * 商品封面
     */
    @ApiModelProperty("商品封面")
    private String thumb;
    /**
     * 商品标签：1高级 2稀有3史诗4传说
     */
    @ApiModelProperty("商品标签：1高级 2稀有3史诗4传说")
    private Integer tag;
    /**
     * 商品内容
     */
    @ApiModelProperty("商品内容")
    private String content;

    @ApiModelProperty("商品价格")
    private BigDecimal pice;
    /**
     * 商品内容
     */
    @ApiModelProperty("商品内容")
    private String mdContent;
    /**
     * 标签名称
     */
    @ApiModelProperty("标签名称")
    private String tagText;




}
