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
 * 积分流水
 * </p>
 *
 * @author Wen·Hui bms
 * @since 2023-02-08
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("store_point_record")
public class StorePointRecord extends Model<StorePointRecord> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 用户id
     */
    private Integer uid;
    /**
     * 来源id
     */
    @TableField("source_id")
    private Integer sourceId;
    /**
     * 1、任务 2、系统赠送 3、订单 4、签到 5、兑换奖品
     */
    @TableField("source_type")
    private Integer sourceType;
    /**
     * 是否过期0 没过期 1 过期
     */
    @TableField("exprie_status")
    private Integer exprieStatus;
    /**
     * in 收入 out 支出
     */
    private String symbol;
    /**
     * 积分值
     */
    private Integer point;
    /**
     * 备注
     */
    private String remark;
    /**
     * 创建时间
     */
    @TableField("created_at")
    private Date createdAt;
    /**
     * 修改时间
     */
    @TableField("updated_at")
    private Date updatedAt;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
