package com.slzh.dao;


import com.slzh.model.SysRole;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface SysRoleMapper {
    Integer insertOne(SysRole sysRole);
    Integer insertRoleMenuMapping(@Param("roleId") Integer roleId, @Param("appIds") List<String> roleMenus);

    /**
     * 删除用户角色映射
     * @param roleId 角色id 有的话就是根据角色删除
     * @param userId 用户id 有的话就是删除用户的时候删除角色
     * @return
     */
    Integer deleteUserRoleMapping(@Param("roleId") Integer roleId, @Param("userId") Integer userId);

    /**
     * 删除角色菜单映射
     * 没有删除菜单的选项 所以不会传菜单id
     * @param roleId
     * @return
     */
    Integer deleteRoleMenuMapping(Integer roleId);

    /**
     * 删除角色
     * @param roleId
     * @return
     */
    Integer delete(Integer roleId);

    List<SysRole> getAllRole(String roleName);

    void update(SysRole sysRole);
    List<Map<String, Object>> getRoleCount();
}
