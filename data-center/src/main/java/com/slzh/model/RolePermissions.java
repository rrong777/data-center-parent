package com.slzh.model;

import java.util.List;

public class RolePermissions {
    private Integer roleId;
    private String roleName;
    private String roleHasMenuPerms;
    private List<String> uri;

    public List<String> getUri() {
        return uri;
    }

    public void setUri(List<String> uri) {
        this.uri = uri;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleHasMenuPerms() {
        return roleHasMenuPerms;
    }

    public void setRoleHasMenuPerms(String roleHasMenuPerms) {
        this.roleHasMenuPerms = roleHasMenuPerms;
    }
}
