package com.slzh.model.desktop;

import java.util.List;

public class FolderInfo implements DesktopInterface {
    //id
    private String id;

    //文件夹id
    private String folderId;
    //桌面id
    private String desktopId;
    //文件夹名称
    private String name;
    //图标路径
    private String icon;
    //文件夹宽度
    private int width;
    //文件夹高度
    private int height;
    //打开
    private int open;
    //关闭
    private int expand;
    //桌面的顺序
    private int sequence;
    //类型
    private String type;
    //app信息
    private List<AppInfo> appInfoList2;

    @Override
    public String getId(){
        return id;
    }

    @Override
    public void setId(String id){
        this.id = id;
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
        this.desktopId = desktopId ;
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
        this.icon = icon;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getOpen() {
        return open;
    }

    public void setOpen(int open) {
        this.open = open;
    }

    public int getExpand() {
        return expand;
    }

    public void setExpand(int expand) {
        this.expand = expand;
    }

    @Override
    public int getSequence() {
        return sequence;
    }

    public void setSequence(int sequence) {
        this.sequence = sequence;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<AppInfo> getAppInfoList() {
        return appInfoList2;
    }

    public void setAppInfoList(List<AppInfo> appInfoList) {
        this.appInfoList2 = appInfoList;
    }
}
