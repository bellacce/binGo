package com.wenhui.project.dal.mybatis.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.wenhui.project.dal.mybatis.dataobject.OrderDetail;
import com.wenhui.project.web.vo.OrderRollListVo;

import java.util.List;

/**
 * <p>
 * 订单商品表 Mapper 接口
 * </p>
 *
 * @author FU·HAO
 * @since 2023-02-20
 */
public interface OrderDetailMapper extends BaseMapper<OrderDetail> {

    List<OrderRollListVo> orderRollList();
}
