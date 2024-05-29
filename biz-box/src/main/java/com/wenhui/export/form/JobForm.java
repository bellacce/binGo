package com.wenhui.export.form;

import lombok.Data;

@Data
public class JobForm {

    private String jobName;

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }
}
