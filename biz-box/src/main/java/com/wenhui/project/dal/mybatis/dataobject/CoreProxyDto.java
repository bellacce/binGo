package com.wenhui.project.dal.mybatis.dataobject;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author xinghe
 * @date 2023-06-13
 * @Description
 */
@Data
public class CoreProxyDto {

    private String mobile;

    private String name;

    private BigDecimal amount;

}
