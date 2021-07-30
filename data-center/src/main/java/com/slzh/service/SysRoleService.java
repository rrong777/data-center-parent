package com.slzh.service;

import com.slzh.model.SysRole;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

public interface SysRoleService {
    @Transactional
    Integer saveOrUpdate(SysRole sysRole) throws Exception;
    Integer delete(Integer roleId) throws Exception;
    List<SysRole> get(String roleName);
}
