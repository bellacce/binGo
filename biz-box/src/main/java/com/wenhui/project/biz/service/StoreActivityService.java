package com.wenhui.project.biz.service;

import com.baomidou.mybatisplus.service.IService;
import com.wenhui.project.dal.mybatis.dataobject.StoreActivity;
import com.wenhui.project.web.dto.StoreActivityDto;

import java.util.List;

/**
 * <p>
 * 活动管理 服务类
 * </p>
 *
 * @author Wen·Hui bms
 * @since 2023-02-08
 */
public interface StoreActivityService extends IService<StoreActivity> {


    List<StoreActivityDto> queryList();
}
