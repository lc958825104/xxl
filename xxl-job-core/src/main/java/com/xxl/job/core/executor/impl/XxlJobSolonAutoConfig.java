package com.xxl.job.core.executor.impl;

import org.noear.solon.annotation.Bean;
import org.noear.solon.annotation.Configuration;
import org.noear.solon.annotation.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author noear 2021/5/22 created
 */
@Configuration
public class XxlJobSolonAutoConfig {
    private static final Logger logger = LoggerFactory.getLogger(XxlJobSolonAutoConfig.class);

    @Inject("${xxl.job.admin.addresses}")
    private String adminAddresses;

    @Inject("${xxl.job.accessToken}")
    private String accessToken;

    @Inject("${xxl.job.executor.appname}")
    private String appname;

    @Inject("${xxl.job.executor.address}")
    private String address;

    @Inject("${xxl.job.executor.ip}")
    private String ip;

    @Inject("${xxl.job.executor.port}")
    private int port;

    @Inject("${xxl.job.executor.logpath}")
    private String logPath;

    @Inject("${xxl.job.executor.logretentiondays}")
    private int logRetentionDays;

    @Bean
    public XxlJobSolonExecutor xxlJobExecutor() {
        logger.info(">>>>>>>>>>> xxl-job config init.");

        XxlJobSolonExecutor executor = new XxlJobSolonExecutor();

        executor.setAdminAddresses(adminAddresses);
        executor.setAppname(appname);
        executor.setAddress(address);
        executor.setIp(ip);
        executor.setPort(port);
        executor.setAccessToken(accessToken);
        executor.setLogPath(logPath);
        executor.setLogRetentionDays(logRetentionDays);

        return executor;
    }
}
