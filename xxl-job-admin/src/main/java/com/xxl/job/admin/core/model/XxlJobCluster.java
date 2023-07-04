package com.xxl.job.admin.core.model;

import java.util.Date;

public class XxlJobCluster {

    private String hostName;

    private Date updateTime;

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
