package com.xxl.job.admin.core.conf;

import com.xxl.job.admin.core.alarm.JobAlarmer;
import com.xxl.job.admin.core.model.XxlJobCluster;
import com.xxl.job.admin.core.model.XxlJobInfo;
import com.xxl.job.admin.core.scheduler.XxlJobScheduler;
import com.xxl.job.admin.dao.*;
import com.xxl.job.admin.service.JobAllocation;
import com.xxl.job.admin.service.impl.AverageJobAllocation;
import com.xxl.job.core.util.IpUtil;
import io.micrometer.core.instrument.util.StringUtils;
import io.netty.util.internal.StringUtil;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.Arrays;
import java.util.List;

/**
 * xxl-job config
 *
 * @author xuxueli 2017-04-28
 */

@Component
public class XxlJobAdminConfig implements InitializingBean, DisposableBean {

    private static XxlJobAdminConfig adminConfig = null;

    public static XxlJobAdminConfig getAdminConfig() {
        return adminConfig;
    }


    // ---------------------- XxlJobScheduler ----------------------

    private XxlJobScheduler xxlJobScheduler;

    @Override
    public void afterPropertiesSet() throws Exception {
        adminConfig = this;
        getJobAllocation().init(true);
        xxlJobScheduler = new XxlJobScheduler();
        xxlJobScheduler.init();
        getJobAllocation().flush();
    }

    @Override
    public void destroy() throws Exception {
        xxlJobScheduler.destroy();
    }


    // ---------------------- XxlJobScheduler ----------------------

    // conf
    @Value("${xxl.job.i18n}")
    private String i18n;

    @Value("${xxl.job.accessToken}")
    private String accessToken;

    @Value("${spring.mail.from}")
    private String emailFrom;

    @Value("${xxl.job.triggerpool.fast.max}")
    private int triggerPoolFastMax;

    @Value("${xxl.job.triggerpool.slow.max}")
    private int triggerPoolSlowMax;

    @Value("${xxl.job.logretentiondays}")
    private int logretentiondays;


    @Value("${xxl.job.cluster.host.name}")
    private String hostName;

    @Value("${server.port}")
    private int port;

    @Value("${xxl.job.cluster.enable:false}")
    private boolean clusterEnable;


    // dao, service

    @Resource
    private XxlJobLogDao xxlJobLogDao;
    @Resource
    private XxlJobInfoDao xxlJobInfoDao;
    @Resource
    private XxlJobRegistryDao xxlJobRegistryDao;
    @Resource
    private XxlJobGroupDao xxlJobGroupDao;
    @Resource
    private XxlJobLogReportDao xxlJobLogReportDao;
    @Resource
    private XxlJobClusterDao xxlJobClusterDao;
    @Resource
    private JavaMailSender mailSender;
    @Resource
    private DataSource dataSource;
    @Resource
    private JobAlarmer jobAlarmer;

    public String getHostName() {
        return StringUtils.isBlank(hostName) ? IpUtil.getIpPort(port) : hostName;
    }

    private JobAllocation jobAllocation = defaultJobAllocation;
    private static JobAllocation defaultJobAllocation = new JobAllocation() {
        @Override
        public void allocation(XxlJobInfo jobInfo) {
        }

        @Override
        public void init(boolean init) {
        }
    };

    public String getI18n() {
        if (!Arrays.asList("zh_CN", "zh_TC", "en").contains(i18n)) {
            return "zh_CN";
        }
        return i18n;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getEmailFrom() {
        return emailFrom;
    }

    public int getTriggerPoolFastMax() {
        if (triggerPoolFastMax < 200) {
            return 200;
        }
        return triggerPoolFastMax;
    }

    public int getTriggerPoolSlowMax() {
        if (triggerPoolSlowMax < 100) {
            return 100;
        }
        return triggerPoolSlowMax;
    }

    public int getLogretentiondays() {
        if (logretentiondays < 7) {
            return -1;  // Limit greater than or equal to 7, otherwise close
        }
        return logretentiondays;
    }

    public XxlJobLogDao getXxlJobLogDao() {
        return xxlJobLogDao;
    }

    public XxlJobInfoDao getXxlJobInfoDao() {
        return xxlJobInfoDao;
    }

    public XxlJobRegistryDao getXxlJobRegistryDao() {
        return xxlJobRegistryDao;
    }

    public XxlJobGroupDao getXxlJobGroupDao() {
        return xxlJobGroupDao;
    }

    public XxlJobLogReportDao getXxlJobLogReportDao() {
        return xxlJobLogReportDao;
    }

    public JavaMailSender getMailSender() {
        return mailSender;
    }

    public DataSource getDataSource() {
        return dataSource;
    }

    public JobAlarmer getJobAlarmer() {
        return jobAlarmer;
    }

    public XxlJobClusterDao getXxlJobClusterDao() {
        return xxlJobClusterDao;
    }

    public JobAllocation getJobAllocation() {
        return jobAllocation.equals(defaultJobAllocation) && clusterEnable ? new AverageJobAllocation() : defaultJobAllocation;
    }

    public void setJobAllocation(JobAllocation jobAllocation) {
        this.jobAllocation = jobAllocation;
    }

    public boolean isClusterEnable() {
        return clusterEnable;
    }
}
