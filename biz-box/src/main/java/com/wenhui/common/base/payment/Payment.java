package com.wenhui.common.base.payment;

import com.wenhui.project.biz.service.LogSaveService;
import com.wenhui.project.web.bo.PaymentResultBo;
import com.wenhui.project.web.dto.PayParamDto;

import java.util.Map;

public abstract class Payment {
    public abstract PaymentResultBo pay(PayParamDto payParamDto, LogSaveService logSaveService);
}
