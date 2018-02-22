package com.infincash.statistics.risk.table;

public class TUserRisk {
    private String id;

    private String userId;

    private String riskRuleId;

    private String checkId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public String getRiskRuleId() {
        return riskRuleId;
    }

    public void setRiskRuleId(String riskRuleId) {
        this.riskRuleId = riskRuleId == null ? null : riskRuleId.trim();
    }

    public String getCheckId() {
        return checkId;
    }

    public void setCheckId(String checkId) {
        this.checkId = checkId == null ? null : checkId.trim();
    }
}