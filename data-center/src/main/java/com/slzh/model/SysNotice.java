package com.slzh.model;

import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SysNotice {
    private Long id;
    private String message;
    private Long userId;
    private String createdTime;
    private String updatedTime;
    private Long associationId;
    private int status;
    private String username;
    private String imgUrls;
    private String redirectUrl;
    private String typeDesc;
    private String noticeOrigin;
    private String noticePusher;
    private String label;
    private int showType;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getShowType() {
        return showType;
    }

    public void setShowType(int showType) {
        this.showType = showType;
    }

    public static void main(String[] args) {
        Map<String, Object> params = new HashMap<>();
        String[] arr = {"02-22","02-21","02-20","02-19","02-18","02-17","02-16"};
        params.put("date", arr);
        System.out.println(JSONObject.toJSONString(params));;


//        Map<String, Object> params = new HashMap<>();
//        String[] arr1 = {"0","1","1","0","2","3","5"};
//        String[] arr2 = {"0","1","0","4","7","0","0"};
//        String[] arr3 = {"1","0","5","0","4","0","1"};
//        String[] arr4 = {"0","2","9","0","0","0","0"};
//        params.put("personData", arr1);
//        params.put("vehicleData", arr2);
//        params.put("macData", arr3);
//        params.put("wifiData", arr4);
//        System.out.println(JSONObject.toJSONString(params));;
    }
    public String getNoticeOrigin() {
        return noticeOrigin;
    }

    public void setNoticeOrigin(String noticeOrigin) {
        this.noticeOrigin = noticeOrigin;
    }

    public String getNoticePusher() {
        return noticePusher;
    }

    public void setNoticePusher(String noticePusher) {
        this.noticePusher = noticePusher;
    }



    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getImgUrls() {
        return imgUrls;
    }

    public void setImgUrls(String imgUrls) {
        this.imgUrls = imgUrls;
    }

    public String getRedirectUrl() {
        return redirectUrl;
    }

    public void setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }

    public String getTypeDesc() {
        return typeDesc;
    }

    public void setTypeDesc(String typeDesc) {
        this.typeDesc = typeDesc;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getCreatedTime() {
        return createdTime;
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

    public Long getAssociationId() {
        return associationId;
    }

    public void setAssociationId(Long associationId) {
        this.associationId = associationId;
    }
}
