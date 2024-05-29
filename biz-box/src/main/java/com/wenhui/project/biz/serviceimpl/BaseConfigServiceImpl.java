package com.wenhui.project.biz.serviceimpl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.wenhui.common.base.utils.PageDto;
import com.wenhui.core.base.utils.common.util.BusinessException;
import com.wenhui.core.core.biz.ErrorCode;
import com.wenhui.integration.cos.CosUpload;
import com.wenhui.integration.cos.CosUploadConstant;
import com.wenhui.integration.sms.tencent.TencentSmsConstant;
import com.wenhui.project.biz.config.OrdersRebateConfig;
import com.wenhui.project.biz.config.UserShareUrlConfig;
import com.wenhui.project.biz.service.BaseConfigService;
import com.wenhui.project.dal.mybatis.dao.BaseConfigMapper;
import com.wenhui.project.dal.mybatis.dataobject.BaseConfig;
import com.wenhui.project.web.dto.BaseConfigDto;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 基本配置 服务实现类
 * </p>
 *
 * @author Wen·Hui bms
 * @since 2023-02-08
 */
@Service
@Slf4j
public class BaseConfigServiceImpl extends ServiceImpl<BaseConfigMapper, BaseConfig> implements BaseConfigService {

    @Override
    public String addPhoto(MultipartFile file) {
        String filePath = "";
        if (Objects.nonNull(file)) {
            try {
                filePath = CosUpload.upload(file, "/shop/image/feedback");
            } catch (Exception e) {
                e.printStackTrace();
                new BusinessException(String.valueOf(ErrorCode.SERVER_ERROR),"上传失败");
            }
            log.info("upload COS successful: {}", filePath);
        }
        return filePath;
    }

    @Override
    public boolean addConfig(BaseConfigDto baseConfigDto) {
        BaseConfig baseConfig = new BaseConfig();
        BeanUtils.copyProperties(baseConfigDto, baseConfig);
        baseConfig.setType(Integer.parseInt(baseConfigDto.getType()));
        if (Objects.nonNull(baseConfigDto.getId())){
            boolean result = this.updateById(baseConfig);
            this.updateConfigToActivate(Integer.parseInt(baseConfigDto.getType()));
            return result;
        }else{
            baseConfig.setCreatedAt(new Date());
            boolean result = this.insert(baseConfig);
            return result;
        }
    }

    @Override
    public Page<BaseConfigDto> listConfig(PageDto pageDto) {
        Page<BaseConfig> baseConfigs = this.selectPage(new Page<>(pageDto.getCurrentPage(), pageDto.getSize()),new EntityWrapper<BaseConfig>().eq(StringUtils.isNotEmpty(pageDto.getId()),"type",pageDto.getId()).ne("type",1));

        Page<BaseConfigDto> resultPage = new Page<>(pageDto.getCurrentPage(), pageDto.getSize());
        List<BaseConfigDto> collect = baseConfigs.getRecords().stream().map(c -> {
            BaseConfigDto baseConfigDto = new BaseConfigDto();
            BeanUtils.copyProperties(c, baseConfigDto);
            baseConfigDto.setType(c.getType()+"");
            switch (baseConfigDto.getType()){
                case "2":
                    baseConfigDto.setTypeString("视频");
                    break;
                case "3":
                    baseConfigDto.setTypeString("腾讯sms");
                    break;
                case "4":
                    baseConfigDto.setTypeString("腾讯cos");
                    break;
                case "5":
                    baseConfigDto.setTypeString("收益划分");
                    break;
                case "6":
                    baseConfigDto.setTypeString("域名链接配置");
                    break;
                default:
                    break;
            }
            return baseConfigDto;
        }).collect(Collectors.toList());
        resultPage.setRecords(collect);
        resultPage.setTotal(baseConfigs.getTotal());
        return resultPage;
    }

    @Override
    public boolean deleteConfig(Integer id) {
        boolean result = this.deleteById(id);
        return result;
    }

    @Override
    public List<Map<String, String>> sysPrint() {
        List<Map<String, String>> result = new ArrayList<>();
        result.add(TencentSmsConstant.sysPrint());
        result.add(CosUploadConstant.sysPrint());
        result.add(OrdersRebateConfig.sysPrint());
        return result;
    }

    @Override
    public void updateConfigToActivate(Integer type) {
        List<BaseConfig> baseConfigs = this.selectList(new EntityWrapper<BaseConfig>().eq("type", type));
        Map<String,String> map = baseConfigs.stream().collect(Collectors.toMap(BaseConfig::getCode,BaseConfig::getContent));
        switch (type){
            case 3:
                //腾讯sms
                TencentSmsConstant.REGION = map.get("region");
                TencentSmsConstant.SECRET_ID = map.get("secret_id");
                TencentSmsConstant.SECRET_KEY = map.get("secret_key");
                TencentSmsConstant.ENDPOINT = map.get("endpoint");
                TencentSmsConstant.SMS_SDK_APP_ID = map.get("sms_sdk_app_id");
                TencentSmsConstant.SIGN_NAME = map.get("sign_name");
                TencentSmsConstant.TEMPLATE_ID = map.get("template_id");
                TencentSmsConstant.EXPIRED_TIME = map.get("expired_time");
                break;
            case 4:
                //腾讯cos
                CosUploadConstant.SECRET_ID = map.get("secretId");
                CosUploadConstant.SECRET_KEY = map.get("secretKey");
                CosUploadConstant.DURATION_SECONDS = Integer.parseInt(map.get("durationSeconds"));
                CosUploadConstant.BUCKET = map.get("bucket");
                CosUploadConstant.REGION = map.get("region");
                CosUploadConstant.BASEURL = map.get("baseurl");
                break;
            case 5:
                //收益划分
                OrdersRebateConfig.USER_REBATE = new BigDecimal(map.get("userRebate"));
                OrdersRebateConfig.USER_RECOMMEND_REBATE = new BigDecimal(map.get("userRecommendRebate"));
                OrdersRebateConfig.TERRACE_REBATE = new BigDecimal(map.get("terraceRebate"));
                break;
            case 6:
                //域名链接配置
                UserShareUrlConfig.FIRST_INVITATION_LINK = map.get("firstInvitationLink");
                UserShareUrlConfig.SECOND_INVITATION_LINK = map.get("secondInvitationLink");
                UserShareUrlConfig.THIRD_INVITATION_LINK = map.get("thirdInvitationLink");
                break;
            default:
                break;
        }
    }

}
