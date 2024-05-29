package com.wenhui.project.biz.serviceimpl;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.wenhui.project.biz.service.StoreBlindboxGoodsService;
import com.wenhui.project.biz.service.StoreGoodsService;
import com.wenhui.project.dal.mybatis.dao.StoreBlindboxGoodsMapper;
import com.wenhui.project.dal.mybatis.dataobject.StoreBlindboxGoods;
import com.wenhui.project.dal.mybatis.dataobject.StoreGoods;
import com.wenhui.project.web.dto.AdminBoxGoodsAddDto;
import com.wenhui.project.web.dto.AdminBoxGoodsDetailDto;
import com.wenhui.project.web.dto.BoxGoodsBriefDescDto;
import com.wenhui.project.web.dto.StoreBlindboxGoodsDto;
import com.wenhui.project.web.vo.AdminBoxGoodsDetailVo;
import com.wenhui.project.web.vo.ImageBaseVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * <p>
 * 盲盒商品表 服务实现类
 * </p>
 *
 * @author Fu·Hao
 * @since 2023-02-17
 */
@Service
public class StoreBlindboxGoodsServiceImpl extends ServiceImpl<StoreBlindboxGoodsMapper, StoreBlindboxGoods> implements StoreBlindboxGoodsService {


    @Resource
    private StoreBlindboxGoodsMapper storeBlindboxGoodsMapperMapper;
    @Autowired
    private StoreGoodsService storeGoodsService;
    /**
     * 通过盲盒id获取盲盒内的商品列表
     *
     * @param boxId
     * @return
     */
    @Override
    public List<StoreBlindboxGoodsDto> queryGoodsList(Integer boxId) {
        return storeBlindboxGoodsMapperMapper.queryGoodsList(boxId);
    }

    /**
     * 根据商品id获取商品简要信息
     *
     * @param id
     * @return
     */
    @Override
    public BoxGoodsBriefDescDto queryGoods(Integer id) {
        return storeBlindboxGoodsMapperMapper.queryGoods(id);
    }

    @Override
    public Page<AdminBoxGoodsDetailVo> getBoxDetail(AdminBoxGoodsDetailDto adminBoxGoodsDetailDto) {
        Page<AdminBoxGoodsDetailVo> resultPage = new Page<>(adminBoxGoodsDetailDto.getCurrentPage(), adminBoxGoodsDetailDto.getSize());
        Page<StoreBlindboxGoods> storeUsers = this.selectPage(new Page<>(adminBoxGoodsDetailDto.getCurrentPage(), adminBoxGoodsDetailDto.getSize()), new EntityWrapper<StoreBlindboxGoods>()
                .eq(Objects.nonNull(adminBoxGoodsDetailDto.getBoxId()),"box_id", adminBoxGoodsDetailDto.getBoxId())
                .eq(Objects.nonNull(adminBoxGoodsDetailDto.getCategoryId()),"category_id", adminBoxGoodsDetailDto.getCategoryId())
                .eq(Objects.nonNull(adminBoxGoodsDetailDto.getTag()),"tag", adminBoxGoodsDetailDto.getTag())
                .eq("delete_flag", 0));

        List<AdminBoxGoodsDetailVo> collect = storeUsers.getRecords().stream().map(u -> {
            AdminBoxGoodsDetailVo adminBoxGoodsDetailVo = new AdminBoxGoodsDetailVo();
            adminBoxGoodsDetailVo.setThumbs(JSONArray.parseArray(u.getThumb()).toJavaList(ImageBaseVo.class));
            BeanUtils.copyProperties(u, adminBoxGoodsDetailVo);
            return adminBoxGoodsDetailVo;
        }).collect(Collectors.toList());
        resultPage.setRecords(collect);
        resultPage.setTotal(storeUsers.getTotal());
        return resultPage;
    }

    @Override
    public Boolean addBoxShop(AdminBoxGoodsAddDto adminBoxGoodsAddDto) {
        StoreBlindboxGoods storeBlindboxGoods = new StoreBlindboxGoods();
        BeanUtils.copyProperties(adminBoxGoodsAddDto,storeBlindboxGoods);
        if (Objects.nonNull(adminBoxGoodsAddDto.getGoodsNo())){
            StoreGoods storeGoods = storeGoodsService.selectById(adminBoxGoodsAddDto.getGoodsNo());
            storeBlindboxGoods.setName(storeGoods.getGoodsName());
            storeBlindboxGoods.setThumb(storeGoods.getGoodsThumb());
            storeBlindboxGoods.setPice(storeGoods.getGoodsPrice());
            storeBlindboxGoods.setContent(storeGoods.getContent());
            storeBlindboxGoods.setCategoryId(storeGoods.getGoodsType());
        }
        if (Objects.nonNull(adminBoxGoodsAddDto.getId())){
            storeBlindboxGoods.setId(adminBoxGoodsAddDto.getId());
            return this.updateById(storeBlindboxGoods);
        }else{
            storeBlindboxGoods.setAddTime(new Date());
            return this.insert(storeBlindboxGoods);
        }
    }

    @Override
    public Boolean deleteBoxShop(Integer boxId, Integer id) {
        boolean result = this.updateForSet("delete_flag=1", new EntityWrapper<StoreBlindboxGoods>().eq("box_id", boxId).eq("id", id));
        return result;
    }
}
