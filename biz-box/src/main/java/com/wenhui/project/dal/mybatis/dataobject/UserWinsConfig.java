package com.wenhui.project.dal.mybatis.dataobject;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotations.Version;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author FU·HAO
 * @since 2023-04-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("user_wins_config")
public class UserWinsConfig extends Model<UserWinsConfig> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 用户id
     */
    @TableField("user_id")
    private Integer userId;
    /**
     * 盲盒id
     */
    @TableField("box_id")
    private Integer boxId;
    /**
     * 高级商品概率
     */
    @TableField("hign_probability")
    private Double hignProbability;
    /**
     * 稀有商品概率
     */
    @TableField("rarity_probability")
    private Double rarityProbability;
    /**
     * 史诗商品概率
     */
    @TableField("epic_probability")
    private Double epicProbability;
    /**
     * 传说商品概率
     */
    @TableField("legend_probability")
    private Double legendProbability;
    /**
     * 0启用，1删除
     */
    @TableField("delete_flag")
    private Integer deleteFlag;
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
    /**
     * 购买次数
     */
    @TableField("purchases_num")
    private Integer purchasesNum;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
