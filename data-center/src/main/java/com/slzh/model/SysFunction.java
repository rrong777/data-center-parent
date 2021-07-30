package com.slzh.model;

import java.util.List;
import java.util.Set;

public class SysFunction {
    private Integer id;
    private String code;
    private String name;
    // 下面两个是一样的，只不过一个是对象，一个是字符串，对象是因为要返回给前台直接从数据库查询 mybatis 的collection标签查出对象
    // string是自己构造
    private List<Interfaces> interfaces;
    private List<String> interfaceStrs;

    private Set<Integer> hasFunctionRoleIds;
    public List<String> getInterfaceStrs() {
        return interfaceStrs;
    }

    public Set<Integer> getHasFunctionRoleIds() {
        return hasFunctionRoleIds;
    }

    public void setHasFunctionRoleIds(Set<Integer> hasFunctionRoleIds) {
        this.hasFunctionRoleIds = hasFunctionRoleIds;
    }

    public void setInterfaceStrs(List<String> interfaceStrs) {
        this.interfaceStrs = interfaceStrs;
    }

    public List<Interfaces> getInterfaces() {
        return interfaces;
    }

    public void setInterfaces(List<Interfaces> interfaces) {
        this.interfaces = interfaces;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
