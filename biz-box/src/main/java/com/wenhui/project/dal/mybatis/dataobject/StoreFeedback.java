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
 *
 * </p>
 *
 * @author Wen·Hui bms
 * @since 2023-02-12
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("store_feedback")
public class StoreFeedback extends Model<StoreFeedback> {

    private static final long serialVersionUID = 1L;

    /**
     * 问题描述
     */
    @TableField("problem_describe")
    private String problemDescribe;
    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 联系方式
     */
    private String phone;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 截图
     */
    private String images;
    /**
     * 视频
     */
    private String videos;
    /**
     * 提交用户
     */
    private Integer uid;

    private Date createTime;

    private Date updateTime;

    private Integer isDelete;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
