package com.wenhui.project.biz.serviceimpl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.wenhui.project.biz.service.StoreActivityService;
import com.wenhui.project.dal.mybatis.dao.StoreActivityMapper;
import com.wenhui.project.dal.mybatis.dataobject.StoreActivity;
import com.wenhui.project.web.dto.StoreActivityDto;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 活动管理 服务实现类
 * </p>
 *
 * @author Wen·Hui bms
 * @since 2023-02-08
 */
@Service
public class StoreActivityServiceImpl extends ServiceImpl<StoreActivityMapper, StoreActivity> implements StoreActivityService {

    @Resource
    private StoreActivityMapper storeActivityMapper;

    public List<StoreActivityDto> queryList() {
        List<StoreActivityDto> storeActivities = storeActivityMapper.queryList();
        return storeActivities;
    }
}
