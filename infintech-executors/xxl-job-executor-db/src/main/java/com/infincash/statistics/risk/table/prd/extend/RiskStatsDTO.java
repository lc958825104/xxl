package com.infincash.statistics.risk.table.prd.extend;

import java.util.Date;

public class RiskStatsDTO {
	String riskRuleId;
    int count;
    Date time;
    
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	public String getRiskRuleId() {
		return riskRuleId;
	}
	public void setRiskRuleId(String riskRuleId) {
		this.riskRuleId = riskRuleId;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}    
}
