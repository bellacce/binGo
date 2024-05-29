package com.wenhui.project.biz.serviceimpl;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.wenhui.common.base.utils.MyBeanUtils;
import com.wenhui.core.base.utils.common.util.BusinessException;
import com.wenhui.core.core.biz.ErrorCode;
import com.wenhui.project.biz.service.StoreBlindboxGoodsService;
import com.wenhui.project.biz.service.StoreCategoryService;
import com.wenhui.project.biz.service.StoreGoodsService;
import com.wenhui.project.dal.mybatis.dao.StoreGoodsMapper;
import com.wenhui.project.dal.mybatis.dataobject.StoreBlindboxGoods;
import com.wenhui.project.dal.mybatis.dataobject.StoreCategory;
import com.wenhui.project.dal.mybatis.dataobject.StoreGoods;
import com.wenhui.project.web.dto.GoodUpdateDto;
import com.wenhui.project.web.dto.GoodsListDto;
import com.wenhui.project.web.dto.GoodsPromotionDto;
import com.wenhui.project.web.vo.GoodsListVo;
import com.wenhui.project.web.vo.ImageBaseVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * <p>
 * 商品 服务实现类
 * </p>
 *
 * @author Wen·Hui bms
 * @since 2023-02-08
 */
@Service

public class StoreGoodsServiceImpl extends ServiceImpl<StoreGoodsMapper, StoreGoods> implements StoreGoodsService {

    @Resource
    private StoreGoodsMapper storeGoodsMapper;
    @Resource
    private StoreBlindboxGoodsService storeBlindboxGoodsService;
    @Autowired
    private StoreCategoryService storeCategoryService;

    @Override
    public List<GoodsPromotionDto> goodsPromotion(Integer type) {
        List<GoodsPromotionDto> goodsPromotionDto = storeGoodsMapper.goodsPromotion(type);
        return goodsPromotionDto;
    }

    /**
     * 查询单个商品信息
     *
     * @param goodsId
     * @return
     */
    @Override
    public List<GoodsPromotionDto> productDetails(Integer goodsId) {
        return storeGoodsMapper.productDetails(goodsId);
    }

    @Override
    public Page<GoodsListVo> getShopList(GoodsListDto goodsListDto) {
        Page<GoodsListVo> objectPage = new Page<>(goodsListDto.getCurrentPage(), goodsListDto.getSize());
        Page<StoreGoods> storeGoodsPage = this.selectPage(new Page<>(goodsListDto.getCurrentPage(), goodsListDto.getSize()), new EntityWrapper<StoreGoods>()
                .like(StringUtils.isNotEmpty(goodsListDto.getSearch()), "goods_name", goodsListDto.getSearch())
                .eq(Objects.nonNull(goodsListDto.getGoodsType()), "goods_type", goodsListDto.getGoodsType()).eq("delete_flag", 0)
        );
        List<GoodsListVo> collect = storeGoodsPage.getRecords().stream().map(g -> {
            GoodsListVo goodsListVo = new GoodsListVo();
            BeanUtils.copyProperties(g, goodsListVo);
            StoreCategory storeCategory = storeCategoryService.selectById(g.getGoodsType());
            goodsListVo.setGoodsTypeString(storeCategory.getName());
            goodsListVo.setStatus(g.getStatus()==1?true:false);
            goodsListVo.setThumbs(JSONArray.parseArray(g.getGoodsThumb()).toJavaList(ImageBaseVo.class));
            goodsListVo.setIsBoxGoodString(goodsListVo.getIsBoxGood()==0?"商城商品":"盲盒商品");
            goodsListVo.setGoodsThumb(JSONArray.parseArray(g.getGoodsThumb()).toJavaList(ImageBaseVo.class).get(0).getUrl());
            goodsListVo.setGoodsCover(JSONArray.parseArray(g.getGoodsCover()).toJavaList(ImageBaseVo.class).stream().map(ImageBaseVo::getUrl).collect(Collectors.toList()));
            return goodsListVo;
        }).collect(Collectors.toList());
        objectPage.setRecords(collect);
        objectPage.setTotal(storeGoodsPage.getTotal());
        return objectPage;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean addShopInfo(GoodUpdateDto goodUpdateDto) {
        StoreGoods storeGoods = new StoreGoods();
        BeanUtils.copyProperties(goodUpdateDto, storeGoods, MyBeanUtils.getNullPropertyNames(goodUpdateDto));
        if(StringUtils.isNotEmpty(goodUpdateDto.getGoodsThumb())){
            List<ImageBaseVo> objects = new ArrayList<>();
            ImageBaseVo imageBaseVo = new ImageBaseVo();
            imageBaseVo.setName("商品封面");
            imageBaseVo.setUrl(goodUpdateDto.getGoodsThumb());
            objects.add(imageBaseVo);
            storeGoods.setGoodsThumb(JSONArray.toJSONString(objects));
        }
        if(!CollectionUtils.isEmpty(goodUpdateDto.getGoodsCover())){
            List<ImageBaseVo> objects = new ArrayList<>();
            goodUpdateDto.getGoodsCover().forEach(i->{
                ImageBaseVo imageBaseVo = new ImageBaseVo();
                imageBaseVo.setName("商品轮播图封面");
                imageBaseVo.setUrl((String) i);
                objects.add(imageBaseVo);
            });
            storeGoods.setGoodsCover(JSONArray.toJSONString(objects));
        }
        if (Objects.nonNull(goodUpdateDto.getGoodsId())){
            List<StoreBlindboxGoods> goods_no = storeBlindboxGoodsService.selectList(new EntityWrapper<StoreBlindboxGoods>().eq("goods_no", goodUpdateDto.getGoodsId() + ""));
            if (goods_no.size()>0){
                if (goodUpdateDto.getIsBoxGood()==0){
                    throw new BusinessException(String.valueOf(ErrorCode.DATA_EMPTY_DATA),"商品已被绑定，不能修改商品类型");
                }
                goods_no.forEach(g->{
                    g.setCategoryId(goodUpdateDto.getGoodsType());
                    g.setName(storeGoods.getGoodsName());
                    g.setThumb(storeGoods.getGoodsThumb());
                    g.setPice(storeGoods.getGoodsPrice());
                    g.setContent(storeGoods.getContent());
                    g.setMdContent(storeGoods.getMdContent());
                });
            }
            boolean b = storeBlindboxGoodsService.updateAllColumnBatchById(goods_no);
            storeGoods.setGoodsId(goodUpdateDto.getGoodsId());
            boolean update = this.updateById(storeGoods);
            return update;
        }else{
            boolean insert = this.insert(storeGoods);
            return insert;
        }
    }


    @Override
    public Boolean changeShopStatus(String id, Boolean status) {
        String[] split = id.split(",");
        for (String s : split) {
            this.updateForSet("status="+(status?1:2), new EntityWrapper<StoreGoods>().eq("goods_id", s));
        }
        return true;
    }

    @Override
    public Boolean deleteShopList(String id) {
        String[] split = id.split(",");
        for (String s : split) {
            this.updateForSet("delete_flag=1", new EntityWrapper<StoreGoods>().eq("goods_id", s));
        }
        return true;
    }
}
