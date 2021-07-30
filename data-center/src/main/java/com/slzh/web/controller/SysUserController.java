package com.slzh.web.controller;

import com.slzh.model.SysUser;
import com.slzh.model.http.HttpResult;
import com.slzh.model.page.PageRequest;
import com.slzh.service.SysUserService;
import com.slzh.utils.login.SecurityUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 用户控制器
 *
 * @author lanb
 * @date Jan 13, 2019
 */
@RestController
@RequestMapping("/user")
public class SysUserController {
    private final static Logger logger = LoggerFactory.getLogger(SysUserController.class);
    @Autowired
    private SysUserService sysUserService;

    @PostMapping("/findPage")
    public HttpResult findPage(@RequestBody PageRequest pageRequest) {
        return HttpResult.ok(sysUserService.findPage(pageRequest));
    }

    @PostMapping("/setUserRole")
    public HttpResult setUserRole(@RequestBody Map<String, Object> params) {
        if (params.get("userId") != null) {
            Long userId = Long.valueOf((String) params.get("userId"));
            if (params.get("roleList") != null) {
                List<Integer> roleList = (List<Integer>) params.get("roleList");
                try {
                    return HttpResult.ok(sysUserService.setUserRole(userId, roleList));
                } catch (RuntimeException e) {
                    return HttpResult.error(e.getMessage());
                }
            }
        }
        return null;
    }


    @GetMapping("/getUserInfo")
    public HttpResult getUserInfo() {
        return HttpResult.ok(sysUserService.getUserInfo());
    }

    @PostMapping("/batchAlterUserOrganization")
    public HttpResult batchAlterUserOrganization(@RequestBody Map<String, Object> params) {
        try {
            return HttpResult.ok(sysUserService.batchAlterUserOrganization(params));
        } catch (Exception e) {
            return HttpResult.error("请检查必要参数userIds与organizationId");
        }

    }

    @PostMapping("/alterUserPasw")
    public HttpResult alterUserPasw(@RequestBody SysUser sysUser) {
        try {
            return HttpResult.ok(sysUserService.alterUserPasw(sysUser));
        } catch (RuntimeException e) {
            return HttpResult.error(e.getMessage());
        }

    }



    @PostMapping("/alterUserPaswByAdmin")
    public HttpResult alterUserPaswByAdmin(@RequestBody SysUser sysUser) {
        try {
            return HttpResult.ok(sysUserService.alterUserPaswByAdmin(sysUser));
        } catch (RuntimeException e) {
            return HttpResult.error(e.getMessage());
        }

    }


    @PostMapping("/alterUserStatus")
    public HttpResult alterUserStatus(@RequestBody Map<String, Object> params) {
        try {
            return HttpResult.ok(sysUserService.alterUserStatus(params));
        } catch (Exception e) {
            return HttpResult.error(e.getMessage());
        }
    }


    @GetMapping("delete")
    public HttpResult delete(@RequestParam Long userId) {
        try {
            return HttpResult.ok(sysUserService.delete(userId));
        } catch (RuntimeException e) {
            return HttpResult.error(e.getMessage());
        }

    }

    @PostMapping("/saveOrUpdate")
    public HttpResult saveOrUpdate(@RequestBody SysUser sysUser) {
        if (StringUtils.isAllBlank(sysUser.getPoliceId())) {
            return HttpResult.error("警号不能为空");
        }
        try {
            return HttpResult.ok(sysUserService.saveOrUpdate(sysUser));
        } catch (Exception e) {
            return HttpResult.error(e.getMessage());
        }
    }

}
