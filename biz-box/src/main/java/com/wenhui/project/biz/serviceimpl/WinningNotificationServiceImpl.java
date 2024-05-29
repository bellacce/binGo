package com.wenhui.project.biz.serviceimpl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.wenhui.project.biz.service.WinningNotificationService;
import com.wenhui.project.dal.mybatis.dao.WinningNotificationMapper;
import com.wenhui.project.dal.mybatis.dataobject.WinningNotification;
import com.wenhui.project.web.dto.WinningNotificationDto;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author Fu·Hao
 * @since 2023-02-16
 */
@Service
public class WinningNotificationServiceImpl extends ServiceImpl<WinningNotificationMapper, WinningNotification> implements WinningNotificationService {

    @Resource
    WinningNotificationMapper winningNotificationMapper;

    /**
     * 根据通知类型查询通知内容
     *
     * @param type
     * @return
     */
    @Override
    public List<WinningNotificationDto> queryNotice(Integer type) {
        return winningNotificationMapper.queryNotice(type);
    }
}
