package com.slzh.model;

import java.util.List;
import java.util.Map;

public class ReleaseParams {
    private Map<String, Object> params;
    private Integer interfaceType;
    private Boolean isAllRelease;
    private List<Integer> interfaceInfoIds;

    private List<Long> userIdList;

    public List<Long> getUserIdList() {
        return userIdList;
    }

    public void setUserIdList(List<Long> userIdList) {
        this.userIdList = userIdList;
    }

    public Map<String, Object> getParams() {
        return params;
    }

    public void setParams(Map<String, Object> params) {
        this.params = params;
    }

    public Integer getInterfaceType() {
        return interfaceType;
    }

    public void setInterfaceType(Integer interfaceType) {
        this.interfaceType = interfaceType;
    }

    public Boolean getAllRelease() {
        return isAllRelease;
    }

    public void setAllRelease(Boolean allRelease) {
        isAllRelease = allRelease;
    }

    public List<Integer> getInterfaceInfoIds() {
        return interfaceInfoIds;
    }

    public void setInterfaceInfoIds(List<Integer> interfaceInfoIds) {
        this.interfaceInfoIds = interfaceInfoIds;
    }
}
