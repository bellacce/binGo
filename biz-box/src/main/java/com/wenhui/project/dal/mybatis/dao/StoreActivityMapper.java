package com.wenhui.project.dal.mybatis.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.wenhui.project.dal.mybatis.dataobject.StoreActivity;
import com.wenhui.project.web.dto.StoreActivityDto;

import java.util.List;

/**
 * <p>
 * 活动管理 Mapper 接口
 * </p>
 *
 * @author Wen·Hui bms
 * @since 2023-02-08
 */
public interface StoreActivityMapper extends BaseMapper<StoreActivity> {
    List<StoreActivityDto> queryList();
}
