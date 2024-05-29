package com.wenhui.project.biz.service;

import com.baomidou.mybatisplus.service.IService;
import com.wenhui.project.dal.mybatis.dataobject.WinningNotification;
import com.wenhui.project.web.dto.WinningNotificationDto;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author Fu·Hao
 * @since 2023-02-16
 */
public interface WinningNotificationService extends IService<WinningNotification> {

    /**
     * 根据通知类型查询通知内容
     *
     * @param type
     * @return
     */
    List<WinningNotificationDto> queryNotice(Integer type);

}
