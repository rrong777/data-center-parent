package com.slzh.model;

import java.util.List;

public class SysOrganization {
    private String parentName;
    private Integer level;
    private List<SysOrganization> children;
    private Integer id;
    private String name;
    private String label;
    private Integer parentId;
    private String createdTime;
    private List<SysUser> childrenPersons;

    public List<SysUser> getChildrenPersons() {
        return childrenPersons;
    }

    public void setChildrenPersons(List<SysUser> childrenPersons) {
        this.childrenPersons = childrenPersons;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public List<SysOrganization> getChildren() {
        return children;
    }

    public void setChildren(List<SysOrganization> children) {
        this.children = children;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }
}
