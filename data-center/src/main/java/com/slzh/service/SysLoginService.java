package com.slzh.service;

import com.slzh.config.security.JwtAuthenticatioToken;
import com.slzh.model.LoginBean;
import com.slzh.model.SysUser;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface SysLoginService {

    SysUser login(LoginBean loginBean, HttpServletRequest request) throws Exception;
    void captcha(HttpServletResponse response, HttpServletRequest request, String uuid) throws IOException;

    JwtAuthenticatioToken changeLogin(String userName, HttpServletRequest request);

    SysUser refreshToken();
}
