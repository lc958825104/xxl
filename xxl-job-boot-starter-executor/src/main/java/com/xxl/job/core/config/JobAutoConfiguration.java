package com.xxl.job.core.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

import com.xxl.job.core.executor.impl.XxlJobSpringExecutor;
import com.xxl.job.core.thread.ExecutorRegistryThread;

import lombok.extern.slf4j.Slf4j;

/**
 * 执行器端自动配置
 * @author cqyhm
 */
@Slf4j
@Configuration()
@ConditionalOnClass(ExecutorRegistryThread.class)
@ConditionalOnProperty(prefix =JobConfigProperties.PREFIX ,name = "enabled", matchIfMissing = true)
@EnableConfigurationProperties(value = {JobConfigProperties.class,ApplicationProperties.class})
public class JobAutoConfiguration {

    @Bean
    public XxlJobSpringExecutor xxlJobExecutor(JobConfigProperties jobConfigProperties,
    										   ApplicationProperties applicationProperties) {
        log.info(">>>>>>>>>>> xxl-job config init.");
        log.info("读取配置{}",applicationProperties);
        log.info("读取配置{}",jobConfigProperties);
        XxlJobSpringExecutor JobSpringExecutor = new XxlJobSpringExecutor();
        //通讯令牌
        JobSpringExecutor.setAccessToken(jobConfigProperties.getAccessToken());
        //调度器设置
        JobSpringExecutor.setAdminAddresses(jobConfigProperties.getAdmin().getAddresses());
        //执行器设置
        String name=applicationProperties.getName(); //应用程序的名称
        String appName=jobConfigProperties.getExecutor().getAppname(); //调度器的名称如果没有设置取应用的名称
        JobSpringExecutor.setAppname(StringUtils.hasText(appName) ? name: appName);
        JobSpringExecutor.setIp(jobConfigProperties.getExecutor().getIp());
        JobSpringExecutor.setPort(jobConfigProperties.getExecutor().getPort());
        //执行器日志路径
        JobSpringExecutor.setLogPath(jobConfigProperties.getExecutor().getLogpath());
        JobSpringExecutor.setLogRetentionDays(jobConfigProperties.getExecutor().getLogretentiondays());

        return JobSpringExecutor;
    }
    /**
     * 针对多网卡、容器内部署等情况，可借助 "spring-cloud-commons" 提供的 "InetUtils" 组件灵活定制注册IP；
     *
     *      1、引入依赖：
     *          <dependency>
     *             <groupId>org.springframework.cloud</groupId>
     *             <artifactId>spring-cloud-commons</artifactId>
     *             <version>${version}</version>
     *         </dependency>
     *
     *      2、配置文件，或者容器启动变量
     *          spring.cloud.inetutils.preferred-networks: 'xxx.xxx.xxx.'
     *
     *      3、获取IP
     *          String ip_ = inetUtils.findFirstNonLoopbackHostInfo().getIpAddress();
     */


}