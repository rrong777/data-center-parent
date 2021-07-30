package com.slzh.service.impl;

import com.slzh.dao.SysRoleMapper;
import com.slzh.model.SysRole;
import com.slzh.service.SysRoleService;
import com.slzh.service.SysUserService;
import com.slzh.utils.login.SecurityUtils;
import io.jsonwebtoken.lang.Collections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class SysRoleServiceImpl implements SysRoleService {

    @Autowired
    private SysRoleMapper sysRoleMapper;

    @Autowired
    private SysUserService sysUserService;

    private void saveRoleMenuMapping(SysRole sysRole, boolean isUpdate) {
        if(isUpdate) {
            sysRoleMapper.deleteRoleMenuMapping(sysRole.getId());
        }
        if(!Collections.isEmpty(sysRole.getAppIds())) {
            sysRoleMapper.insertRoleMenuMapping(sysRole.getId(), sysRole.getAppIds());
        }
    }

    @Override
    public Integer saveOrUpdate(SysRole sysRole) throws Exception {
        Integer roleId = sysRole.getId();
        boolean isUpdate = (sysRole.getId() == null ? false : true);
        Long currentUserId = SecurityUtils.getUserId(sysUserService);
        sysRole.setCreatedBy(currentUserId);
        if(roleId != null && (roleId == 1 || roleId == 2)) {
            throw new Exception("无法修改管理员角色！");
        }
        try {
            if(sysRole.getId() == null) {
                sysRoleMapper.insertOne(sysRole);
            } else {
                sysRoleMapper.update(sysRole);
            }
        }catch (Exception e) {
//                name_un_idx
            if (e.getCause().getMessage().indexOf("Duplicate entry") != -1) {
                throw new Exception("重复的角色名！");
            }
        }
        saveRoleMenuMapping(sysRole, isUpdate);
        return 1;
    }



    @Override
    public Integer delete(Integer roleId) throws Exception {
        if(roleId == 1 || roleId == 2) {
            throw new Exception("无法删除管理员权限！");
        }
        sysRoleMapper.deleteUserRoleMapping(roleId, null);
        sysRoleMapper.deleteRoleMenuMapping(roleId);
        sysRoleMapper.delete(roleId);
        return 1;
    }


    @Override
    public List<SysRole> get(String roleName) {
//        getRoleCount
        List<SysRole> allRole = sysRoleMapper.getAllRole(roleName);
        List<Map<String, Object>> roleCount = sysRoleMapper.getRoleCount();
        for(Map<String, Object> tempCount : roleCount) {
            Integer roleId = (Integer) tempCount.get("roleId");
            for(SysRole temp : allRole) {
                Integer tempRoleId = temp.getId();
                if(roleId == tempRoleId) {
                    temp.setCount((Long) tempCount.get("count"));
                    break;
                }

            }

        }


        return allRole;
    }
}
