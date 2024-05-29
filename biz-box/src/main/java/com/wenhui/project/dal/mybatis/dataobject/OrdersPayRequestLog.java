package com.wenhui.project.dal.mybatis.dataobject;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 支付回调日志记录
 * </p>
 *
 * @author FU·HAO
 * @since 2023-02-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("orders_pay_request_log")
public class OrdersPayRequestLog extends Model<OrdersPayRequestLog> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 请求数据
     */
    @TableField("request_data")
    private String requestData;
    /**
     * 响应数据
     */
    @TableField("response_data")
    private String responseData;
    /**
     * 请求地址或回调地址
     */
    private String url;
    /**
     * 请求类型  1：请求第三方 2：第三方回调
     */
    private Integer type;
    /**
     * 创建时间
     */
    @TableField("create_time")
    private Date createTime;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    public OrdersPayRequestLog(String url, String requestData, String responseData, Integer type) {
        this.requestData = requestData;
        this.responseData = responseData;
        this.url = url;
        this.type = type;
    }

    public OrdersPayRequestLog(String url, String requestData, Integer type) {
        this.requestData = requestData;
        this.url = url;
        this.type = type;
    }
}
