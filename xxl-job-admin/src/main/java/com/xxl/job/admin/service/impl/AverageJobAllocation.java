package com.xxl.job.admin.service.impl;

import com.xxl.job.admin.core.conf.XxlJobAdminConfig;
import com.xxl.job.admin.core.model.XxlJobCluster;
import com.xxl.job.admin.core.model.XxlJobInfo;
import com.xxl.job.admin.service.JobAllocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * @author liyong
 */
public class AverageJobAllocation implements JobAllocation {

    private static Logger logger = LoggerFactory.getLogger(AverageJobAllocation.class);


    private static int oneMinute = 60;
    private static int tenMinutes = oneMinute * 10;
    private static int oneHour = tenMinutes * 6;
    private static int oneDay = oneHour * 24;

    private Map<Integer, AtomicInteger> averageMap = new ConcurrentHashMap<>();
    private Map<String, List<String>> ipMap = new ConcurrentHashMap<>();

    {
        // 防止执行频率高的 扎堆在一起
        averageMap.put(oneMinute, new AtomicInteger(0));
        averageMap.put(tenMinutes, new AtomicInteger(0));
        averageMap.put(oneHour, new AtomicInteger(0));
        averageMap.put(oneDay, new AtomicInteger(0));
        averageMap.put(Integer.MAX_VALUE, new AtomicInteger(0));
    }

    @Override
    public void allocation(XxlJobInfo jobInfo) {
        List<XxlJobCluster> all = XxlJobAdminConfig.getAdminConfig().getXxlJobClusterDao().findAll();
        List<String> ipList = all.stream().filter(x -> x.getUpdateTime().getTime() > System.currentTimeMillis() - 60 * 1000)
                .map(XxlJobCluster::getHostName).collect(Collectors.toList());

        if (ipList.isEmpty()) {
            jobInfo.setHostName(XxlJobAdminConfig.getAdminConfig().getHostName());
            return;
        }

        Integer key = getKey(jobInfo.getTriggerNextTime() - System.currentTimeMillis());
        int i = averageMap.get(key).incrementAndGet();

        jobInfo.setHostName(ipList.get(i % ipList.size()));

    }

    @Override
    public void init(boolean init) {
        XxlJobAdminConfig.getAdminConfig().getXxlJobInfoDao().initLockStatus(XxlJobAdminConfig.getAdminConfig().getHostName(),init);
    }

    @Override
    public void flush() {
        Connection conn = null;
        Boolean connAutoCommit = null;
        PreparedStatement preparedStatement = null;

        try {
            conn = XxlJobAdminConfig.getAdminConfig().getDataSource().getConnection();
            connAutoCommit = conn.getAutoCommit();
            conn.setAutoCommit(false);

            preparedStatement = conn.prepareStatement("select * from xxl_job_lock where lock_name = 'flush_lock' for update");
            preparedStatement.execute();

            recursion(0, conn);
        } catch (Exception e) {
            logger.error(">>>>>>>>>>> xxl-job, JobScheduleHelper#scheduleThread error:{}", e);
        } finally {
            // commit
            if (conn != null) {
                try {
                    conn.commit();
                } catch (SQLException e) {
                    logger.error(e.getMessage(), e);
                }
                try {
                    conn.setAutoCommit(connAutoCommit);
                } catch (SQLException e) {
                    logger.error(e.getMessage(), e);
                }
                try {
                    conn.close();
                } catch (SQLException e) {
                    logger.error(e.getMessage(), e);
                }
            }

            // close PreparedStatement
            if (null != preparedStatement) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    logger.error(e.getMessage(), e);
                }
            }
        }
    }

    private void recursion(int id, Connection conn) {
        List<XxlJobInfo> list = XxlJobAdminConfig.getAdminConfig().getXxlJobInfoDao().pageById(id);
        if (CollectionUtils.isEmpty(list))
            return;
        Map<String, List<Integer>> map = new HashMap<>();
        list.forEach(x -> {
            allocation(x);
            List<Integer> ids = map.getOrDefault(x.getHostName(), new ArrayList<>());
            ids.add(x.getId());
            map.put(x.getHostName(), ids);
        });
        map.forEach((k, v) -> XxlJobAdminConfig.getAdminConfig().getXxlJobInfoDao().updateHostNameByIds(k, v));
        try {
            conn.commit();
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        recursion(list.get(list.size() - 1).getId(), conn);
    }

    private Integer getKey(long time) {
        if (time < oneMinute) {
            return oneMinute;
        } else if (time < tenMinutes) {
            return tenMinutes;
        } else if (time < oneHour) {
            return oneHour;
        } else if (time < oneDay) {
            return oneDay;
        } else {
            return Integer.MAX_VALUE;
        }
    }

}
