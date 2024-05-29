package com.wenhui.project.web.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @program: wh_shopbox
 * @description: 获取所有待处理订单Vo
 * @author: Mr.Wang
 * @create: 2023-03-02 23:42
 **/
@Data
@Accessors(chain = true)
public class GetAllOrderDetailVo {

    @ApiModelProperty("虚拟订单")
    private Integer cardSum = 0;

    @ApiModelProperty("实物订单")
    private Integer orderSum;
}
