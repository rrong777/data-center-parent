package com.slzh.web.controller;

import com.slzh.model.SysOrganization;
import com.slzh.model.http.HttpResult;
import com.slzh.service.SysOrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/organization")
public class SysOrganizationController {
    @Autowired
    private SysOrganizationService sysOrganizationService;

    @GetMapping("/delete")
    public HttpResult delete(@RequestParam Integer organizationId) {
        try {
            return HttpResult.ok(sysOrganizationService.delete(organizationId));
        } catch (RuntimeException e) {
            return HttpResult.error(e.getMessage());
        }
    }

    @PostMapping("/saveOrUpdate")
    public HttpResult saveOrUpdate(@RequestBody SysOrganization sysOrganization) {
        return HttpResult.ok(sysOrganizationService.saveOrUpdate(sysOrganization));
    }

    @GetMapping("/getOrganizationPeople")
    public HttpResult getOrganizationPeople(@RequestParam Integer organizationId) {
        return HttpResult.ok(sysOrganizationService.getOrganizationPeople(organizationId));
    }

    @GetMapping("/get")
    public HttpResult get() {
        return HttpResult.ok(sysOrganizationService.get());
    }

    @GetMapping("/getTaskRecevier")
    public HttpResult getTaskRecevier() {
        return HttpResult.ok(sysOrganizationService.getTaskRecevier());
    }

}
