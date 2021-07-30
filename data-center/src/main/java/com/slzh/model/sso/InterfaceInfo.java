package com.slzh.model.sso;

import java.util.Date;

public class InterfaceInfo {
    private Long interfaceInfoId;

    private String registerInfoId;

    private String developers;

    private String applicationName;

    private Integer interfaceType;

    private String interfaceAddresses;

    private String requestMode;

    private Boolean isEnabled;

    private String remark;

    private Boolean isDelete;

    private String createUser;

    private String createDepartment;

    private String createServerHost;

    private Date createTime;

    private String updateUser;

    private String updateDepartment;

    private String updateServerHost;

    private Date updateTime;

    private Integer recordVersion;

    public Long getInterfaceInfoId() {
        return interfaceInfoId;
    }

    public void setInterfaceInfoId(Long interfaceInfoId) {
        this.interfaceInfoId = interfaceInfoId;
    }

    public String getRegisterInfoId() {
        return registerInfoId;
    }

    public void setRegisterInfoId(String registerInfoId) {
        this.registerInfoId = registerInfoId;
    }

    public String getDevelopers() {
        return developers;
    }

    public void setDevelopers(String developers) {
        this.developers = developers == null ? null : developers.trim();
    }

    public String getApplicationName() {
        return applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName == null ? null : applicationName.trim();
    }

    public Integer getInterfaceType() {
        return interfaceType;
    }

    public void setInterfaceType(Integer interfaceType) {
        this.interfaceType = interfaceType;
    }

    public String getInterfaceAddresses() {
        return interfaceAddresses;
    }

    public void setInterfaceAddresses(String interfaceAddresses) {
        this.interfaceAddresses = interfaceAddresses == null ? null : interfaceAddresses.trim();
    }

    public String getRequestMode() {
        return requestMode;
    }

    public void setRequestMode(String requestMode) {
        this.requestMode = requestMode == null ? null : requestMode.trim();
    }

    public Boolean getIsEnabled() {
        return isEnabled;
    }

    public void setIsEnabled(Boolean isEnabled) {
        this.isEnabled = isEnabled;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Boolean getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Boolean isDelete) {
        this.isDelete = isDelete;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser == null ? null : createUser.trim();
    }

    public String getCreateDepartment() {
        return createDepartment;
    }

    public void setCreateDepartment(String createDepartment) {
        this.createDepartment = createDepartment == null ? null : createDepartment.trim();
    }

    public String getCreateServerHost() {
        return createServerHost;
    }

    public void setCreateServerHost(String createServerHost) {
        this.createServerHost = createServerHost == null ? null : createServerHost.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser == null ? null : updateUser.trim();
    }

    public String getUpdateDepartment() {
        return updateDepartment;
    }

    public void setUpdateDepartment(String updateDepartment) {
        this.updateDepartment = updateDepartment == null ? null : updateDepartment.trim();
    }

    public String getUpdateServerHost() {
        return updateServerHost;
    }

    public void setUpdateServerHost(String updateServerHost) {
        this.updateServerHost = updateServerHost == null ? null : updateServerHost.trim();
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getRecordVersion() {
        return recordVersion;
    }

    public void setRecordVersion(Integer recordVersion) {
        this.recordVersion = recordVersion;
    }
}