package com.infincash.statistics.risk.table.stats;

import java.util.Date;

public class TStatsRiskDetail {
    private Long id;

    private Date statsTime;

    private Integer aHourCount;

    private String riskRuleId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getStatsTime() {
        return statsTime;
    }

    public void setStatsTime(Date statsTime) {
        this.statsTime = statsTime;
    }

    public Integer getaHourCount() {
        return aHourCount;
    }

    public void setaHourCount(Integer aHourCount) {
        this.aHourCount = aHourCount;
    }

    public String getRiskRuleId() {
        return riskRuleId;
    }

    public void setRiskRuleId(String riskRuleId) {
        this.riskRuleId = riskRuleId == null ? null : riskRuleId.trim();
    }
}