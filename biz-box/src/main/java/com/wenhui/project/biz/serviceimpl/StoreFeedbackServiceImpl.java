package com.wenhui.project.biz.serviceimpl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.wenhui.common.security.UserThreadLocal;
import com.wenhui.integration.cos.CosUpload;
import com.wenhui.project.biz.service.StoreFeedbackService;
import com.wenhui.project.dal.mybatis.dao.StoreFeedbackMapper;
import com.wenhui.project.dal.mybatis.dataobject.StoreFeedback;
import com.wenhui.project.web.dto.SaveFeedbackDto;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author Wen·Hui bms
 * @since 2023-02-12
 */
@Service
@Slf4j
public class StoreFeedbackServiceImpl extends ServiceImpl<StoreFeedbackMapper, StoreFeedback> implements StoreFeedbackService {

    @Override
    public Boolean saveFeedback(MultipartFile[] multipartImages, MultipartFile[] multipartVideos, SaveFeedbackDto saveFeedbackDto) {
        StoreFeedback storeFeedback = new StoreFeedback();
        saveFeedbackDto.setUid(UserThreadLocal.get().getUserId().intValue());
        BeanUtils.copyProperties(saveFeedbackDto, storeFeedback);
        try {
            fileSave(multipartImages, multipartVideos, storeFeedback);
        } catch (Exception e) {
            e.printStackTrace();
        }
        storeFeedback.setCreateTime(new Date());
        boolean insert = this.insert(storeFeedback);
        return insert;
    }

    private StoreFeedback fileSave(MultipartFile[] multipartImages, MultipartFile[] multipartVideos, StoreFeedback storeFeedback) throws Exception {
        List<String> images = new ArrayList<>();
        List<String> videos = new ArrayList<>();
        if (Objects.nonNull(multipartImages) && multipartImages.length > 0) {
            for (int i = 0; i < multipartImages.length; i++) {
                String filePath = CosUpload.upload(multipartImages[i], "/shop/image/feedback");
                log.info("upload COS successful: {}", filePath);
                images.add(filePath);
            }
        }
        storeFeedback.setImages(StringUtils.join(images, ","));
        if (Objects.nonNull(multipartVideos) && multipartVideos.length > 0) {
            for (int i = 0; i < multipartVideos.length; i++) {
                String filePath = CosUpload.upload(multipartVideos[i], "/shop/video/feedback");
                log.info("upload COS successful: {}", filePath);
                videos.add(filePath);
            }
        }
        storeFeedback.setVideos(StringUtils.join(videos, ","));
        return storeFeedback;
    }
}
