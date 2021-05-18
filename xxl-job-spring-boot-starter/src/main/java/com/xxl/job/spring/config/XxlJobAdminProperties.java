package com.xxl.job.spring.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * admin config
 * @author fanhang
 */
@Configuration
@ConfigurationProperties(prefix = "xxl-job.admin")
public class XxlJobAdminProperties {
    private String addresses;
    private String accessToken;

    public String getAddresses() {
        return addresses;
    }

    public void setAddresses(String addresses) {
        this.addresses = addresses;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}