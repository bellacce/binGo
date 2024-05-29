package com.wenhui.project.web.rest;


import com.wenhui.common.base.aop.annotation.PassToken;
import com.wenhui.core.core.biz.RestBusinessTemplate;
import com.wenhui.core.core.web.CommonRestResult;
import com.wenhui.project.biz.service.StoreFeedbackService;
import com.wenhui.project.web.dto.SaveFeedbackDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

/**
 * <p>
 * 投诉与反馈前端控制器
 * </p>
 *
 * @author Wen·Hui bms
 * @since 2023-02-12
 */
@RestController
@RequestMapping("/storeFeedback")
@Api(tags = "投诉与反馈")
public class StoreFeedbackController {

    @Autowired
    private StoreFeedbackService storeFeedbackService;

    @PostMapping("/save/feedback")
    @ApiOperation(value = "投诉与反馈")
    @PassToken
    public CommonRestResult<Boolean> saveFeedback(@RequestPart MultipartFile[] multipartImages, @RequestPart MultipartFile[] multipartVideos, @Valid SaveFeedbackDto saveFeedbackDto) {
        return RestBusinessTemplate.execute(() -> {
            Boolean result = storeFeedbackService.saveFeedback(multipartImages, multipartVideos, saveFeedbackDto);
            return result;
        });
    }
}

