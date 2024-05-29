package com.wenhui.project.biz.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.wenhui.common.base.utils.PageDto;
import com.wenhui.project.dal.mybatis.dataobject.BaseConfig;
import com.wenhui.project.web.dto.BaseConfigDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 基本配置 服务类
 * </p>
 *
 * @author Wen·Hui bms
 * @since 2023-02-08
 */
public interface BaseConfigService extends IService<BaseConfig> {

    String addPhoto(MultipartFile file);

    boolean addConfig(BaseConfigDto baseConfigDto);

    Page<BaseConfigDto> listConfig(PageDto pageDto);

    boolean deleteConfig(Integer id);

    List<Map<String, String>> sysPrint();

    void updateConfigToActivate(Integer type);
}
