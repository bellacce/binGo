package com.wenhui.project.biz.job;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 抽象任务，用于被继承
 */
public abstract class AbstractJob {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    private ExecutorService executorService = Executors.newFixedThreadPool(20);

    public void asyncRun(String jobName) {
        logger.info("开始执行任务：{}", jobName);

        try {
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    execute();
                }
            });
        } catch (Exception e) {
            logger.info("失败执行任务：{}", jobName, e);
        } finally {
            logger.info("结束执行任务：{}", jobName);
        }
    }

    public void syncRun(String jobName) {
        logger.info("开始执行任务：{}", jobName);

        try {
            execute();
        } catch (Exception e) {
            logger.info("失败执行任务：{}", jobName, e);
        } finally {
            logger.info("结束执行任务：{}", jobName);
        }
    }

    protected abstract void execute();

}
