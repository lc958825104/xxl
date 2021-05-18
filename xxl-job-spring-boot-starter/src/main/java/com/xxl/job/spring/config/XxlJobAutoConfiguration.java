package com.xxl.job.spring.config;

import com.xxl.job.core.executor.impl.XxlJobSpringExecutor;
import com.xxl.job.core.util.IpUtil;
import com.xxl.job.core.util.NetUtil;

import java.io.IOException;
import java.net.*;
import java.util.Enumeration;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.*;

/**
 * @author fanhang
 */
@Configuration
public class XxlJobAutoConfiguration {
    private static final Logger log = LoggerFactory.getLogger(XxlJobAutoConfiguration.class);

    @Bean
    @ConditionalOnProperty(name = "xxl-job.executor.enable", havingValue = "true", matchIfMissing = true)
    public XxlJobSpringExecutor xxlJobSpringExecutor(XxlJobAdminProperties adminProps, XxlJobExecutorProperties executorProps) {
        log.info("xxl-job admin [{}] with properties: {}", adminProps.getAddresses(), executorProps);
        Assert.hasText(adminProps.getAddresses(), "require [xxl-job.admin.addresses]");
        Assert.hasText(executorProps.getAppName(), "require [xxl-job.executor.appName]");
        String ip = resolveIp(executorProps.getIp(), executorProps.getPreferredNetworks());
        Assert.hasLength(ip, "resolve ip failed");
        int port = resolvePort(executorProps.getPortMin(), executorProps.getPortMax());

        XxlJobSpringExecutor xxlJobSpringExecutor = new XxlJobSpringExecutor();
        xxlJobSpringExecutor.setAdminAddresses(adminProps.getAddresses());
        if (StringUtils.hasLength(adminProps.getAccessToken())) {
            xxlJobSpringExecutor.setAccessToken(adminProps.getAccessToken());
        }
        xxlJobSpringExecutor.setAppname(executorProps.getAppName());
        xxlJobSpringExecutor.setPort(port);
        xxlJobSpringExecutor.setLogPath(executorProps.getLogPath());
        xxlJobSpringExecutor.setLogRetentionDays(executorProps.getLogRetentionDays());
        xxlJobSpringExecutor.setIp(ip);
        log.info("xxl-job-executor: (admin:{}, appName:{}, ip={}, port:{})", adminProps.getAddresses(), executorProps.getAppName(), ip, port);
        return xxlJobSpringExecutor;
    }

    private String resolveIp(String ip, Set<String> preferredNetworks) {
        if (StringUtils.hasLength(ip)) {
            // 已设置 ip
            return ip;
        }
        // 未设置 ip
        if (!CollectionUtils.isEmpty(preferredNetworks)) {
            // 已配置 preferredNetworks
            ip = findNonLoopbackPreferredAddress(preferredNetworks);
            if (StringUtils.hasLength(ip)) {
                return ip;
            }
        }
        // 始终都没有找到 ip, 则按 xxl-job-core 默认方式获取
        ip = IpUtil.getIp();
        log.warn("auto get address: {}", ip);
        return ip;
    }

    private int resolvePort(int portMin, int portMax) {
        Assert.state(portMin <= portMax, String.format("invalid port range [%d, %d]", portMin, portMax));
        int port = NetUtil.findAvailablePort(portMax);
        Assert.state(port >= portMin, String.format("find available port [%d] out of bounds: [%d, %d]", port, portMin, portMax));
        return port;
    }

    /**
     * copy from spring-cloud-commons #InetUtils
     *
     * @param preferredNetworks
     * @return
     */
    private String findNonLoopbackPreferredAddress(Set<String> preferredNetworks) {
        try {
            for (Enumeration<NetworkInterface> nics = NetworkInterface.getNetworkInterfaces(); nics.hasMoreElements(); ) {
                NetworkInterface ifc = nics.nextElement();
                if (ifc.isLoopback() || ifc.isVirtual() || !ifc.isUp()) {
                    continue;
                }
                for (Enumeration<InetAddress> addrs = ifc.getInetAddresses(); addrs.hasMoreElements(); ) {
                    InetAddress address = addrs.nextElement();
                    if (address.isLoopbackAddress()) {
                        continue;
                    }
                    for (String preferredNetwork : preferredNetworks) {
                        if (address.getHostAddress().startsWith(preferredNetwork)) {
                            log.debug("resolved preferred ip [{}] from [{} - {}]", address.getHostAddress(), ifc.getName(), ifc.getDisplayName());
                            return address.getHostAddress();
                        }
                    }
                }
            }
        } catch (IOException ex) {
            log.error("Cannot get non-loopback address", ex);
        }
        return null;
    }
}
