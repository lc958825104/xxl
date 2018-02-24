package com.infincash.cron.collection.table;

public class TBizCollectionOverdueBucket {
    private Short id;

    private Short intervalId;

    private Short leftClosedInterval;

    private String tSystemRoleId;

    public Short getId() {
        return id;
    }

    public void setId(Short id) {
        this.id = id;
    }

    public Short getIntervalId() {
        return intervalId;
    }

    public void setIntervalId(Short intervalId) {
        this.intervalId = intervalId;
    }

    public Short getLeftClosedInterval() {
        return leftClosedInterval;
    }

    public void setLeftClosedInterval(Short leftClosedInterval) {
        this.leftClosedInterval = leftClosedInterval;
    }

    public String gettSystemRoleId() {
        return tSystemRoleId;
    }

    public void settSystemRoleId(String tSystemRoleId) {
        this.tSystemRoleId = tSystemRoleId == null ? null : tSystemRoleId.trim();
    }
}