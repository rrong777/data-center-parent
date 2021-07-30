package com.slzh.model.desktop;

import java.util.List;

public class DesktopInfo {
    //桌面id
    private String desktopId;
    //桌面名称
    private String desktopName;
    //用户id
    private long userId;
    //文件夹信息
    private List<FolderInfo> folderInfoList;
    //
    private List<DesktopInterface> desktopInterfaceList;

    public String getDesktopId() {
        return desktopId;
    }

    public void setDesktopId(String desktopId) {
        this.desktopId = desktopId;
    }

    public String getDesktopName() {
        return desktopName;
    }

    public void setDesktopName(String desktopName) {
        this.desktopName = desktopName;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public List<FolderInfo> getFolderInfoList() {
        return folderInfoList;
    }

    public void setFolderInfoList(List<FolderInfo> folderInfoList) {
        this.folderInfoList = folderInfoList;
    }

    public List<DesktopInterface> getDesktopInterfaceList() {
        return desktopInterfaceList;
    }

    public void setDesktopInterfaceList(List<DesktopInterface> desktopInterfaceList) {
        this.desktopInterfaceList = desktopInterfaceList;
    }

}
