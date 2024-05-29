package com.wenhui.project.web.dto;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.annotations.TableField;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class HomeBoxGoods {
    /**
     * 盲盒标题
     */
    private String title;
    /**
     * 盲盒价格
     */
    private BigDecimal price;

    /**
     * 排序
     */
    private Integer sort;
    /**
     * 购买人数
     */
    @TableField("buy_num")
    private Integer buyNum;
    /**
     * 盲盒封面
     */
    private String thumb;

    /**
     * 盒子内商品的图片（最多6张）
     */
    private String goodsThumb;

    /**
     * 盒内商品数量
     */
    private Integer goodsCount;

    /**
     * 盒内商品最低价
     */
    private Integer lowestPrice;

    /**
     * 盒内商品最高价
     */
    private Integer highestPrice;



    public JSONArray getThumb() {
        JSONArray jsonArray = JSON.parseArray(thumb);
        return jsonArray;
    }

    public JSONArray getGoodsThumb() {
        JSONArray jsonArray = JSON.parseArray(goodsThumb);
        return jsonArray;
    }
}
