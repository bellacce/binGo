package com.wenhui.project.biz.util;

import lombok.Data;

/**
 * @program: wh_shopbox
 * @description: 盲盒开启
 * @author: Mr.Wang
 * @create: 2023-02-20 22:58
 **/
@Data
public class OpenBox {
    private String name;
    private double probability;

    public OpenBox(String name, double probability) {
        this.name = name;
        this.probability = probability;
    }
}
