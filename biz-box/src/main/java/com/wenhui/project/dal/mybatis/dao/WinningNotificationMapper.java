package com.wenhui.project.dal.mybatis.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.wenhui.project.dal.mybatis.dataobject.WinningNotification;
import com.wenhui.project.web.dto.WinningNotificationDto;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author Fu·Hao
 * @since 2023-02-16
 */
public interface WinningNotificationMapper extends BaseMapper<WinningNotification> {

    List<WinningNotificationDto> queryNotice(Integer type);
}
