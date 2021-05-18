package com.xxl.job.spring.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.util.StringUtils;

import java.io.File;
import java.util.Set;

/**
 * executor config
 * @author fanhang
 */
@Configuration
@ConfigurationProperties(prefix = "xxl-job.executor")
public class XxlJobExecutorProperties implements EnvironmentAware, InitializingBean {
    private static final Logger log = LoggerFactory.getLogger(XxlJobExecutorProperties.class);
    private static final int PORT_MIN = 30000;
    private static final int PORT_MAX = 49151;

    private Environment environment;

    private boolean enable = true;
    /**
     * executor appName, default ${spring.application.name}
     */
    private String appName;
    /**
     * executor ip
     */
    private String ip;
    /**
     * logPath dir, default ${user.home}/logs/xxl-job/${spring.application.name}
     */
    private String logPath;
    private int logRetentionDays = 30;
    /**
     * port range min
     */
    private int portMin = PORT_MIN;
    /**
     * port range max
     */
    private int portMax = PORT_MAX;
    private Set<String> preferredNetworks;

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getLogPath() {
        return logPath;
    }

    public void setLogPath(String logPath) {
        this.logPath = logPath;
    }

    public int getLogRetentionDays() {
        return logRetentionDays;
    }

    public void setLogRetentionDays(int logRetentionDays) {
        this.logRetentionDays = logRetentionDays;
    }

    public int getPortMin() {
        return portMin;
    }

    public void setPortMin(int portMin) {
        this.portMin = portMin;
    }

    public int getPortMax() {
        return portMax;
    }

    public void setPortMax(int portMax) {
        this.portMax = portMax;
    }

    public Set<String> getPreferredNetworks() {
        return preferredNetworks;
    }

    public void setPreferredNetworks(Set<String> preferredNetworks) {
        this.preferredNetworks = preferredNetworks;
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if (!enable) {
            log.warn("xxl-job-executor DISABLE because [xxl-job.executor.enable] is false ");
            return;
        }
        if (!StringUtils.hasLength(appName)) {
            appName = environment.getProperty("spring.application.name");
        }
        if (!StringUtils.hasLength(logPath)) {
            String userHome = environment.getProperty("user.home");
            logPath = userHome + File.separator + "logs" + File.separator + "xxl-job" + File.separator + appName;
        }
    }

    @Override
    public String toString() {
        return "XxlJobProperties{" +
                "enable=" + enable +
                ", appName='" + appName + '\'' +
                ", ip='" + ip + '\'' +
                ", logPath='" + logPath + '\'' +
                ", logRetentionDays=" + logRetentionDays +
                ", portMin=" + portMin +
                ", portMax=" + portMax +
                ", preferredNetworks=" + preferredNetworks +
                '}';
    }

}