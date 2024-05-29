package com.wenhui.project.biz.service;

import com.wenhui.project.dal.mybatis.dataobject.SysDictItem;
import com.baomidou.mybatisplus.service.IService;
import com.wenhui.project.web.vo.DictItemListVo;

import java.util.List;

/**
 * <p>
 * 字典项 服务类
 * </p>
 *
 * @author FU·HAO
 * @since 2023-03-14
 */
public interface SysDictItemService extends IService<SysDictItem> {

    /**
     * 根据字典项获取字典列表
     * @param dictId
     * @return
     */
    List<DictItemListVo> queryDictItem(String dictId);
}
