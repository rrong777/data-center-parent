package com.slzh.service.impl;


import com.google.code.kaptcha.Producer;
import com.slzh.client.RedisClient;
import com.slzh.config.security.JwtAuthenticatioToken;
import com.slzh.model.LoginBean;
import com.slzh.model.SysUser;

import com.slzh.service.SysLoginService;
import com.slzh.service.SysUserService;

import com.slzh.service.sso.util.SM2Util;
import com.slzh.service.sso.util.Util;
import com.slzh.utils.IOUtils;
import com.slzh.utils.login.PasswordUtils;
import com.slzh.utils.login.SecurityUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class SysLoginServiceImpl implements SysLoginService {
    private static final Logger log = LoggerFactory.getLogger(SysLoginService.class);
    @Autowired
    private Producer producer;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    RedisClient redisClient;
    private static ConcurrentHashMap<String, Integer> kaptchaNoneMap = new ConcurrentHashMap<>();
    private static ConcurrentHashMap<String, Integer> errorKaptchaMap = new ConcurrentHashMap<>();
    private static ConcurrentHashMap<String, Integer> captchaSecurityMap = new ConcurrentHashMap<>();

    @Override
    public SysUser login(LoginBean loginBean, HttpServletRequest request) throws Exception {
        String username = loginBean.getAccount();
        // 用户信息
        SysUser user = sysUserService.findByNameForSecurity(username, true);
        // 账号不存在、密码错误
        if (user == null) {
            throw new Exception("账号不存在");
        }

        List<String> accessUrls = sysUserService.getUserAccessUrls(user.getId());
        user.setAccessUrls(accessUrls);
        String password = loginBean.getPassword();
        if (errorKaptchaMap.containsKey(username) && errorKaptchaMap.get(username) > 100) {
            log.info("注意：可能有暴力破接密码");
            throw new Exception("可能遇到暴力破接密码，请联系管理员，谢谢");
        }
        if (!PasswordUtils.matches(user.getSalt(), password, user.getPassword())) {
            if (errorKaptchaMap.containsKey(username)) {
                errorKaptchaMap.put(username, errorKaptchaMap.get(username) + 1);
            } else {
                errorKaptchaMap.put(username, 1);
            }
            throw new Exception("密码不正确");
        }
        // 账号锁定
        if (user.getStatus() == 0) {
            throw new Exception("账号已被锁定,请联系管理员");
        }

        // 系统登录认证
        JwtAuthenticatioToken token = SecurityUtils.login(request, username, password, authenticationManager);
        if (user.getRoles() != null) {
            user.setRoleNames(Arrays.asList(user.getRoles().split(",").clone()));
        }
        if (user.getRoleIds() != null) {
            user.setRoleIdList(Arrays.asList(user.getRoleIds().split(",").clone()));
        }
        user.setToken(token.getToken());
        String ssoAuthor = Util.encryptBASE64(user.getUsername().getBytes()) +
                "--" + Util.encryptBASE64(user.getSalt().getBytes());

        user.setSSOAuthor(ssoAuthor);
        return user;
    }

    @Override
    public JwtAuthenticatioToken changeLogin(String userName, HttpServletRequest request) {
        SysUser user = sysUserService.findByNameForSecurity(userName, true);
        return SecurityUtils.changeLogin(request, userName, user, authenticationManager);
    }

    @Override
    public void captcha(HttpServletResponse response, HttpServletRequest request, String uuid) throws IOException {
        response.setDateHeader("Expires", 0);
        response.setHeader("Cache-Control",
                "no-store, no-cache, must-revalidate");
        response.addHeader("Cache-Control", "post-check=0, pre-check=0");
        response.setHeader("Pragma", "no-cache");
        response.setContentType("image/jpeg");
        //获取nginx代理下的真实Ip
        String ip = getUserIp(request);
        log.info("当前注册的用户Ip地址为" + ip);
        if (ip != null && !"".equals(ip)) {
            if (captchaSecurityMap != null && captchaSecurityMap.size() > 1000) {
                log.info("图形验证码缓存大于1000：执行清理captchaSecurityMap");
                captchaSecurityMap.clear();
            }
            if (captchaSecurityMap != null && captchaSecurityMap.size() > 0) {
                Integer refreshCaptchaTime = captchaSecurityMap.get(ip);
                if (refreshCaptchaTime == null) {//表示第一次刷新
                    refreshCaptchaTime = 1;
                    captchaSecurityMap.put(ip, refreshCaptchaTime);
                } else {//已经刷新过
                    captchaSecurityMap.put(ip, refreshCaptchaTime + 1);
                }
                if (refreshCaptchaTime >= 50) {//已经超出刷新限制！
                    log.info("有用户正在暴力刷新验证码，次数：" + captchaSecurityMap);
                    return;
                }
            } else {//captchaSecurityMap已经被清空
                captchaSecurityMap = new ConcurrentHashMap<>();
                captchaSecurityMap.put(ip, 1);
            }
        }
        // 生成文字验证码
        String text = producer.createText();
        // 生成图片验证码
        BufferedImage image = producer.createImage(text);
        // 保存到验证码到 session
        //request.getSession().setAttribute(Constants.KAPTCHA_SESSION_KEY, text);
        //将验证码放入redis中
        redisClient.set(uuid, text, 600);
        log.info("redis:<" + uuid + ">,验证码：" + text);
        ServletOutputStream out = response.getOutputStream();
        ImageIO.write(image, "jpeg", out);
        IOUtils.closeQuietly(out);
    }

    private String getUserIp(HttpServletRequest request) {
        String ipAddress = null;
        try {
            ipAddress = request.getHeader("x-forwarded-for");
            if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getHeader("Proxy-Client-IP");
            }
            if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getHeader("WL-Proxy-Client-IP");
            }
            if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getRemoteAddr();
            }
            // 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
            if (ipAddress != null && ipAddress.length() > 15) { // "***.***.***.***".length()
                // = 15
                if (ipAddress.indexOf(",") > 0) {
                    ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
                }
            }
        } catch (Exception e) {
            ipAddress = null;
            log.error("获取ip出错：{}", e.getMessage());

        }

        SecurityUtils.getUserId(sysUserService);
        log.info("请求IP为{}------", ipAddress);
        return ipAddress;
    }

    @Scheduled(cron = "0 0/5 * * * ? ")
    private static void cleanMap() {
        log.info("五分钟清除一次验证码:captchaSecurityMap");
        captchaSecurityMap.clear();
    }

    @Override
    public SysUser refreshToken() {

        SysUser user = SecurityUtils.getUserInfo(sysUserService);

        if (user.getRoles() != null) {
            user.setRoleNames(Arrays.asList(user.getRoles().split(",").clone()));
        }
        if (user.getRoleIds() != null) {
            user.setRoleIdList(Arrays.asList(user.getRoleIds().split(",").clone()));
        }
        user.setToken(SecurityUtils.refreshToken());
        try {
            String ssoAuthor = Util.encryptBASE64(user.getUsername().getBytes()) +
                    "--" + Util.encryptBASE64(user.getSalt().getBytes());
            user.setSSOAuthor(ssoAuthor);
        } catch (Exception e) {
            log.error("refreshToken err:");
            e.printStackTrace();
        }
        return user;

    }

}
