package com.infincash.statistics.risk.table;

import java.math.BigDecimal;
import java.util.Date;

public class TUser {
    private String id;

    private String userId;

    private String userName;

    private String loginName;

    private String password;

    private String tranPassword;

    private String phone;

    private String creditRuleId;

    private String imageUrl;

    private BigDecimal score;

    private BigDecimal borrowingLines;

    private BigDecimal creditLines;

    private String source;

    private String userType;

    private Integer errrorNumber;

    private Integer tranPwdErrorNum;

    private String explains;

    private String isLocked;

    private String isBlacklist;

    private String inviteCode;

    private String registerIp;

    private Date registerTime;

    private String lastLoginIp;

    private Date lastLoginTime;

    private Date updateTime;

    private String email;

    private Date creditTime;

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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName == null ? null : loginName.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getTranPassword() {
        return tranPassword;
    }

    public void setTranPassword(String tranPassword) {
        this.tranPassword = tranPassword == null ? null : tranPassword.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getCreditRuleId() {
        return creditRuleId;
    }

    public void setCreditRuleId(String creditRuleId) {
        this.creditRuleId = creditRuleId == null ? null : creditRuleId.trim();
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl == null ? null : imageUrl.trim();
    }

    public BigDecimal getScore() {
        return score;
    }

    public void setScore(BigDecimal score) {
        this.score = score;
    }

    public BigDecimal getBorrowingLines() {
        return borrowingLines;
    }

    public void setBorrowingLines(BigDecimal borrowingLines) {
        this.borrowingLines = borrowingLines;
    }

    public BigDecimal getCreditLines() {
        return creditLines;
    }

    public void setCreditLines(BigDecimal creditLines) {
        this.creditLines = creditLines;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source == null ? null : source.trim();
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType == null ? null : userType.trim();
    }

    public Integer getErrrorNumber() {
        return errrorNumber;
    }

    public void setErrrorNumber(Integer errrorNumber) {
        this.errrorNumber = errrorNumber;
    }

    public Integer getTranPwdErrorNum() {
        return tranPwdErrorNum;
    }

    public void setTranPwdErrorNum(Integer tranPwdErrorNum) {
        this.tranPwdErrorNum = tranPwdErrorNum;
    }

    public String getExplains() {
        return explains;
    }

    public void setExplains(String explains) {
        this.explains = explains == null ? null : explains.trim();
    }

    public String getIsLocked() {
        return isLocked;
    }

    public void setIsLocked(String isLocked) {
        this.isLocked = isLocked == null ? null : isLocked.trim();
    }

    public String getIsBlacklist() {
        return isBlacklist;
    }

    public void setIsBlacklist(String isBlacklist) {
        this.isBlacklist = isBlacklist == null ? null : isBlacklist.trim();
    }

    public String getInviteCode() {
        return inviteCode;
    }

    public void setInviteCode(String inviteCode) {
        this.inviteCode = inviteCode == null ? null : inviteCode.trim();
    }

    public String getRegisterIp() {
        return registerIp;
    }

    public void setRegisterIp(String registerIp) {
        this.registerIp = registerIp == null ? null : registerIp.trim();
    }

    public Date getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(Date registerTime) {
        this.registerTime = registerTime;
    }

    public String getLastLoginIp() {
        return lastLoginIp;
    }

    public void setLastLoginIp(String lastLoginIp) {
        this.lastLoginIp = lastLoginIp == null ? null : lastLoginIp.trim();
    }

    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public Date getCreditTime() {
        return creditTime;
    }

    public void setCreditTime(Date creditTime) {
        this.creditTime = creditTime;
    }
}