package com.slzh.service;


import com.slzh.config.security.JwtUserDetails;
import com.slzh.model.SysUser;
import com.slzh.model.http.HttpResult;
import com.slzh.model.page.PageRequest;
import com.slzh.model.page.PageResult;

import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

public interface SysUserService {
    SysUser findByNameForSecurity(String username,boolean isLogin);
    SysUser findByIdForSecurity(Long userId);
    List<String> getUserRolesList(Long userId);

    void decodeOutPassword(SysUser sysUser);

    PageResult findPage(PageRequest pageRequest);
    Integer alterUserPasw(SysUser sysUser);
    @Transactional
    Integer setUserRole(Long userId, List<Integer> roleList);
    Map<String, String> getRolesByUsername(String username);
    /**
     * 管理员和超管可以调用这个接口
     * 但是修改管理员权限只能是超管
     * @param sysUser
     * @return
     * @throws Exception
     */
    Long saveOrUpdate(SysUser sysUser) throws Exception;

    Integer alterUserPaswByAdmin(SysUser sysUser) throws RuntimeException;

    Integer delete(long userId) throws RuntimeException;

    String alterUserStatus(Map<String, Object> params) throws RuntimeException;

    Integer batchAlterUserOrganization( Map<String, Object> params) throws Exception;

    JwtUserDetails getUserInfo();

    List<String> getUserAccessUrls(Long userId);
}
