package com.slzh.dao;


import com.slzh.model.SysUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface SysUserMapper {
    SysUser findByNameForSecurity(String username);
    List<String> getUserRoles(Long userId);
    SysUser findByIdForSecurity(Long userId);
    List<SysUser> findPage(@Param("params") Map<String, Object> params);
    Integer insertOne(SysUser sysUser);
    Integer update(SysUser sysUser);
    Integer delete(Long userId);
    Integer deleteUserRoleMapping(Long userId);
    List<String> getRoleListByDB(Long userId);
    Integer insertUserRoleMapping(@Param("userId") Long userId,@Param("roleList") List<Integer> roleList);
    Integer setUserRole(@Param("userId") Long userId, @Param("roleIds") List<Integer> roleIds);

    Integer alterUserPasw(SysUser sysUser);

    /**
     * 查询list中userId 映射的角色id
     * @return
     */
    List<Integer> findRoleIdsByList(@Param("userIds")List<Integer> userIds);

    Integer batchAlterStatus(@Param("userIds") List<Integer> userIds, @Param("status")Integer status);

    Integer batchAlterUserOrganization(@Param("userIds") List<Integer> userIds, @Param("organizationId") Integer organizationId);

    Map<String, String> getRolesByUsername(String username);

    Map<String, String> getOriginalPasw(@Param("id") Long id);

    String getUserHabit(Long userId);

    Integer setUserHabit(@Param("userId") Long userId, @Param("userHabit") String userHabit);

    List<String> getUserAccessUrls(Long userId);
}