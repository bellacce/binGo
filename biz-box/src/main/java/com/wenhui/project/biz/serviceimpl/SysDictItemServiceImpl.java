package com.wenhui.project.biz.serviceimpl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.wenhui.project.dal.mybatis.dataobject.SysDictItem;
import com.wenhui.project.dal.mybatis.dao.SysDictItemMapper;
import com.wenhui.project.biz.service.SysDictItemService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.wenhui.project.web.vo.DictItemListVo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * 字典项 服务实现类
 * </p>
 *
 * @author FU·HAO
 * @since 2023-03-14
 */
@Service
public class SysDictItemServiceImpl extends ServiceImpl<SysDictItemMapper, SysDictItem> implements SysDictItemService {

    /**
     * 根据字典项获取字典列表
     *
     * @param dictId
     * @return
     */
    @Override
    public List<DictItemListVo> queryDictItem(String dictId) {
        ArrayList<DictItemListVo> dictItemListVos = new ArrayList<>();
        List<SysDictItem> sysDictItems = this.selectList(new EntityWrapper<SysDictItem>().eq("dict_id", dictId).eq("del_flag", 0).eq("status",1).orderAsc(Arrays.asList(new String[]{"sort_order"})));
        sysDictItems.stream().forEach(o->{
            DictItemListVo dictItemListVo = new DictItemListVo();
            BeanUtils.copyProperties(o,dictItemListVo);
            dictItemListVos.add(dictItemListVo);
        });
        return dictItemListVos;
    }
}
