package com.slzh.web.controller;


import com.slzh.client.RedisClient;
import com.slzh.model.LoginBean;
import com.slzh.model.http.HttpResult;
import com.slzh.service.SysLoginService;
import com.slzh.utils.login.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
public class SysLoginController {
    private static final Logger log = LoggerFactory.getLogger(SysLoginController.class);


    @Autowired
    private SysLoginService sysLoginService;


    @Autowired
    RedisClient redisClient;

    @PostMapping("/captcha.jpg")
    public void captcha(HttpServletResponse response, HttpServletRequest request, @RequestBody String uuid) throws ServletException, IOException {
        sysLoginService.captcha(response, request, uuid);
    }

    /**
     * 登录接口
     */
    @PostMapping(value = "/login")
    public HttpResult login(@RequestBody LoginBean loginBean, HttpServletRequest request) throws IOException {
        try {
            return HttpResult.ok(sysLoginService.login(loginBean, request));
        } catch (Exception e) {
            return HttpResult.error(e.getMessage());
        }
    }


    @RequestMapping(value = "/checkIsAccess")
    public HttpResult checkIsAccess() throws IOException {
        log.info("Authorization上来认证:");
        return HttpResult.ok();
    }

    @GetMapping(value = "/changelogin")
    public HttpResult changeLogin(@RequestParam("userName") String userName, HttpServletRequest request) throws IOException {
        return HttpResult.ok(sysLoginService.changeLogin(userName, request));
    }

    @GetMapping("/refreshToken")
    public HttpResult refreshToken() {
        return HttpResult.ok(sysLoginService.refreshToken());
    }
}
