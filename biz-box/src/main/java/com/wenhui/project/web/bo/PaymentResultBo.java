package com.wenhui.project.web.bo;


import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class PaymentResultBo {

    @ApiModelProperty("返回类型，1是跳转链接、2是图片 3、html 4、json")
    private Integer resultType;

    @ApiModelProperty("响应码")
    private Integer code;

    @ApiModelProperty("响应url")
    private String url;

    @ApiModelProperty("响应信息")
    private String resquestInfo;

    @ApiModelProperty("数据")
    private JSONObject json;

}
