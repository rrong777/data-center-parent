package com.slzh.web.controller;

import com.slzh.model.SysRole;
import com.slzh.model.http.HttpResult;
import com.slzh.service.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/role")
public class SysRoleController {
    @Autowired
    private SysRoleService sysRoleService;

    @GetMapping("/get")
    public HttpResult get(@RequestParam(required = false) String roleName){
        return HttpResult.ok(sysRoleService.get(roleName));
    }

    @GetMapping("/delete")
    public HttpResult delete(@RequestParam Integer roleId) {
        try {
            return HttpResult.ok(sysRoleService.delete(roleId));
        } catch (Exception e) {
            return HttpResult.error(e.getMessage());
        }

    }


    @PostMapping("/saveOrUpdate")
    public HttpResult saveOrUpdate(@RequestBody SysRole sysRole) {
        try {
            return HttpResult.ok(sysRoleService.saveOrUpdate(sysRole));
        } catch (Exception e) {
            return HttpResult.error(e.getMessage());
        }
    }
}
