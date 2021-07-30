package com.slzh.model;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SysReport {
    private Long id;
    private String reportName;
    private String xData;
    private String yDatas;
    private Integer type;
    private String createdTime;
    private String updatedTime;
    private String dataOrigin;
    private String dataPusher;

    public String getDataOrigin() {
        return dataOrigin;
    }

    public void setDataOrigin(String dataOrigin) {
        this.dataOrigin = dataOrigin;
    }

    public String getDataPusher() {
        return dataPusher;
    }

    public void setDataPusher(String dataPusher) {
        this.dataPusher = dataPusher;
    }

    public static void main(String[] args) {
        Object object = JSONObject.parseObject("1");
        System.out.println(object);
    }
    public String getCreatedTime() {
        return createdTime;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

    public String getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(String updatedTime) {
        this.updatedTime = updatedTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReportName() {
        return reportName;
    }

    public void setReportName(String reportName) {
        this.reportName = reportName;
    }

    public String getxData() {
        return xData;
    }

    public void setxData(String xData) {
        this.xData = xData;
    }

    public String getyDatas() {
        return yDatas;
    }

    public void setyDatas(String yDatas) {
        this.yDatas = yDatas;
    }


}
