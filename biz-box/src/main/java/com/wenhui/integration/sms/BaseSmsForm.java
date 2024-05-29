package com.wenhui.integration.sms;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;

@Data
public class BaseSmsForm implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    /**
     * 应用代码
     */
    private String appCode;

    private String requestId;

    /**
     * 业务流水号
     */
    private String bizNo;

    @NotBlank(message = "应用代码不能为空")
    private String bizCode;

    @NotBlank(message = "子业务代码不能为空")
    private String subBizCode;
}
