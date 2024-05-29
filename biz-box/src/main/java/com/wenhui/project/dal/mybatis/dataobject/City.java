package com.wenhui.project.dal.mybatis.dataobject;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

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
@TableName("city")
public class City extends Model<City> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 行政区划代码
     */
    private Integer code;
    /**
     * 名称
     */
    private String name;
    /**
     * 上级id
     */
    private Integer pid;
    /**
     * 类型
     */
    private String type;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
