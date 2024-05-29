package com.wenhui.project.biz.service;

import com.baomidou.mybatisplus.service.IService;
import com.wenhui.project.dal.mybatis.dataobject.StoreFeedback;
import com.wenhui.project.web.dto.SaveFeedbackDto;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author Wen·Hui bms
 * @since 2023-02-12
 */
public interface StoreFeedbackService extends IService<StoreFeedback> {

    Boolean saveFeedback(MultipartFile[] multipartImages, MultipartFile[] multipartVideos, SaveFeedbackDto saveFeedbackDto);
}
