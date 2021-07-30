package com.slzh.web.controller;

import com.slzh.model.SysReport;
import com.slzh.model.http.HttpResult;
import com.slzh.model.page.PageRequest;
import com.slzh.service.SysReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/report")
public class SysReportController {
    @Autowired
    private SysReportService sysReportService;

    @PostMapping("/findPage")
    public HttpResult findPage(@RequestBody PageRequest pageRequest) {
        try {
            return HttpResult.ok(sysReportService.findPage(pageRequest));
        } catch (Exception e) {
            return HttpResult.error(e.getMessage());
        }

    }

    @PostMapping("/receiveReport")
    public HttpResult receiveReport(@RequestBody SysReport report) {
        try {
            return sysReportService.receiveReport(report);
        } catch (IllegalArgumentException e) {
            return HttpResult.error(e.getMessage());
        } catch (Exception e) {
            return HttpResult.error(e.getMessage());
        }
    }
}
