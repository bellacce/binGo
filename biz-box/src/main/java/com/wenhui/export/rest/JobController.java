package com.wenhui.export.rest;


import com.wenhui.core.core.biz.AbstractJob;
import com.wenhui.core.core.biz.ApplicationContextUtil;
import com.wenhui.core.core.biz.RestBusinessTemplate;
import com.wenhui.core.core.web.CommonRestResult;
import com.wenhui.export.form.JobForm;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * JOB控制器
 */
@RestController
@RequestMapping("/services/job/")
public class JobController {

    @PostMapping(value = "trigger")
    public CommonRestResult trigger(@RequestBody(required = true) JobForm form) {
        return RestBusinessTemplate.execute(() -> {
            AbstractJob job = (AbstractJob) ApplicationContextUtil.getBean(form.getJobName());
            if (null == job) {
                return "fail";
            }

            job.asyncRun(form.getJobName());

            return "success";
        });
    }

}
