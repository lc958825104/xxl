package com.xxl.job.executor.sample.frameless;

import com.xxl.job.executor.sample.frameless.config.FrameLessXxlJobConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.jfunc.common.thread.HoldProcessor;

import java.util.concurrent.TimeUnit;

/**
 * @author xuxueli 2018-10-31 19:05:43
 */
public class FramelessApplication {
    private static Logger logger = LoggerFactory.getLogger(FramelessApplication.class);

    public static void main(String[] args) {

        try {
            // start
            FrameLessXxlJobConfig.getInstance().initXxlJobExecutor();

            waitHere();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }

    }

    private static void waitHere() {
        final HoldProcessor holdProcessor = new HoldProcessor();
        holdProcessor.startAwait();
        logger.info("程序开始等待");
        Runtime.getRuntime().addShutdownHook(new Thread(){
            @Override
            public void run() {
                logger.info("收到kill 信号，执行清理程序");
                //在关闭的时候释放资源
                FrameLessXxlJobConfig.getInstance().destoryXxlJobExecutor();
                holdProcessor.stopAwait();
            }
        });
    }

}
