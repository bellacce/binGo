package com.wenhui.project.dal.mybatis.dataobject;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 *
 * </p>
 *
 * @author Fu·Hao
 * @since 2023-02-16
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("winning_notification")
public class WinningNotification extends Model<WinningNotification> {

    private static final long serialVersionUID = 1L;

    private Integer id;
    /**
     * 通知内容
     */
    @TableField("notic_name")
    private String noticName;
    /**
     * 创建时间
     */
    @TableField("created_at")
    private Date createdAt;
    /**
     * 更新时间
     */
    @TableField("updated_at")
    private Date updatedAt;
    /**
     * 删除状态，0数据正常，1数据删除
     */
    @TableField("delete_flag")
    private Integer deleteFlag;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
