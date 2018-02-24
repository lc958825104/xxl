package com.infincash.cron.collection.table;

import java.math.BigDecimal;
import java.util.Date;

import com.alibaba.fastjson.JSON;

public class TBizCollection {
    private Long id;

    private String fkTProject;

    private String fkTUser;

    private String fkSystemUser;

    private String projectNumber;

    private String userLoginName;

    private String userRealName;

    private String userPhone;

    private Date loanTime;

    private Integer deadline;
    
    private String projectPeriod;
    
    private String unit;

    private BigDecimal firstPriceLoan;

    private Date repaymentDate;

    private Short overdueDayCount;

    private Short fkTBizCollectionOverdueBucketIntervalId;

    private String collectorLoginName;

    private Date updateTime;

    private String updateBy;

    private int state;

    private Date fullRepayDate;

    private Date lastCollectionTime;

    private Date nextCollectionTime;

    private Short histryCollectionCount;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFkTProject() {
        return fkTProject;
    }

    public void setFkTProject(String fkTProject) {
        this.fkTProject = fkTProject == null ? null : fkTProject.trim();
    }

    public String getFkTUser() {
        return fkTUser;
    }

    public void setFkTUser(String fkTUser) {
        this.fkTUser = fkTUser == null ? null : fkTUser.trim();
    }

    public String getFkSystemUser() {
        return fkSystemUser;
    }

    public void setFkSystemUser(String fkSystemUser) {
        this.fkSystemUser = fkSystemUser == null ? null : fkSystemUser.trim();
    }

    public String getProjectNumber() {
        return projectNumber;
    }

    public void setProjectNumber(String projectNumber) {
        this.projectNumber = projectNumber == null ? null : projectNumber.trim();
    }

    public String getUserLoginName() {
        return userLoginName;
    }

    public void setUserLoginName(String userLoginName) {
        this.userLoginName = userLoginName == null ? null : userLoginName.trim();
    }

    public String getUserRealName() {
        return userRealName;
    }

    public void setUserRealName(String userRealName) {
        this.userRealName = userRealName == null ? null : userRealName.trim();
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone == null ? null : userPhone.trim();
    }

    public Date getLoanTime() {
        return loanTime;
    }

    public void setLoanTime(Date loanTime) {
        this.loanTime = loanTime;
    }

    public BigDecimal getFirstPriceLoan() {
        return firstPriceLoan;
    }

    public void setFirstPriceLoan(BigDecimal firstPriceLoan) {
        this.firstPriceLoan = firstPriceLoan;
    }

    public Date getRepaymentDate() {
        return repaymentDate;
    }

    public void setRepaymentDate(Date repaymentDate) {
        this.repaymentDate = repaymentDate;
    }

    public Short getOverdueDayCount() {
        return overdueDayCount;
    }

    public void setOverdueDayCount(Short overdueDayCount) {
        this.overdueDayCount = overdueDayCount;
    }

    public Short getFkTBizCollectionOverdueBucketIntervalId() {
        return fkTBizCollectionOverdueBucketIntervalId;
    }

    public void setFkTBizCollectionOverdueBucketIntervalId(Short fkTBizCollectionOverdueBucketIntervalId) {
        this.fkTBizCollectionOverdueBucketIntervalId = fkTBizCollectionOverdueBucketIntervalId;
    }

    public String getCollectorLoginName() {
        return collectorLoginName;
    }

    public void setCollectorLoginName(String collectorLoginName) {
        this.collectorLoginName = collectorLoginName == null ? null : collectorLoginName.trim();
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy == null ? null : updateBy.trim();
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public Date getFullRepayDate() {
        return fullRepayDate;
    }

    public void setFullRepayDate(Date fullRepayDate) {
        this.fullRepayDate = fullRepayDate;
    }

    public Date getLastCollectionTime() {
        return lastCollectionTime;
    }

    public void setLastCollectionTime(Date lastCollectionTime) {
        this.lastCollectionTime = lastCollectionTime;
    }

    public Date getNextCollectionTime() {
        return nextCollectionTime;
    }

    public void setNextCollectionTime(Date nextCollectionTime) {
        this.nextCollectionTime = nextCollectionTime;
    }

    public Short getHistryCollectionCount() {
        return histryCollectionCount;
    }

    public void setHistryCollectionCount(Short histryCollectionCount) {
        this.histryCollectionCount = histryCollectionCount;
    }

	public Integer getDeadline()
	{
		return deadline;
	}

	public void setDeadline(Integer deadline)
	{
		this.deadline = deadline;
	}

	public String getUnit()
	{
		return unit;
	}

	public void setUnit(String unit)
	{
		this.unit = unit;
	}
	
	public String getProjectPeriod()
	{
		return projectPeriod;
	}

	public void setProjectPeriod(String projectPeriod)
	{
		this.projectPeriod = projectPeriod;
	}

	@Override
	public String toString() {
		return JSON.toJSONString(this);
	}

}