package com.slzh.web.controller;

import com.alibaba.fastjson.JSONObject;
import com.slzh.model.http.HttpResult;
import com.slzh.model.page.PageRequest;
import com.slzh.service.SysLogService;
import com.slzh.utils.IPUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/log")
public class SysLogController {
    private static final List<String> OPERATION_DESC_LIST = new ArrayList<>();
    static {
        OPERATION_DESC_LIST.add("行人搜索");
        OPERATION_DESC_LIST.add("全息档案 - 人档案");
        OPERATION_DESC_LIST.add("以图搜图 - 搜车");
        OPERATION_DESC_LIST.add("以图搜图 - 搜人");
        OPERATION_DESC_LIST.add("全息档案 - 车档案");
        OPERATION_DESC_LIST.add("登录");
        OPERATION_DESC_LIST.add("模糊搜索");
    }
    @Autowired
    private SysLogService sysLogService;

    @GetMapping("/getOperationDesc")
    public HttpResult getOperationDesc() {
        return HttpResult.ok(OPERATION_DESC_LIST);
    }
    @PostMapping("/findPage")
    public HttpResult findPage(@RequestBody PageRequest pageRequest) {
        return HttpResult.ok(sysLogService.findPage(pageRequest));
    }

    @PostMapping("/addLogByRoute")
    public HttpResult addLogByRoute(@RequestBody JSONObject pathObj, HttpServletRequest request) {
        String path = pathObj.getString("path");
        String ip = IPUtils.getUserIp(request);
        return HttpResult.ok(sysLogService.addLogByRoute(path, ip));
    }
}
