package com.slzh.model.desktop;

import java.util.List;

public class AppInfo implements DesktopInterface{
    //id
    private String id;
    //appId
    private String appId;
    //文件夹id
    private String folderId;
    //桌面id
    private String desktopId;
    //app名称
    private String name;
    //图标路径
    private String icon;
    //桌面的顺序
    private int sequence;
    //文件夹中的顺序
    private int folderSequence;
    //类型
    private String type;
    //link
    private String link;

    private String loginLink;

    public String getLoginLink() {
        return loginLink;
    }

    public void setLoginLink(String loginLink) {
        this.loginLink = loginLink;
    }

    @Override
    public String getId(){
        return id;
    }

    @Override
    public void setId(String id){
        this.id = id;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getFolderId() {
        return folderId;
    }

    public void setFolderId(String folderId) {
        this.folderId = folderId;
    }

    @Override
    public String getDesktopId() {
        return desktopId;
    }

    public void setDesktopId(String desktopId) {
        this.desktopId = desktopId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon ;
    }

    @Override
    public int getSequence() {
        return sequence;
    }

    public void setSequence(int sequence) {
        this.sequence = sequence;
    }

    public int getFolderSequence() {
        return folderSequence;
    }

    public void setFolderSequence(int folderSequence) {
        this.folderSequence = folderSequence;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
