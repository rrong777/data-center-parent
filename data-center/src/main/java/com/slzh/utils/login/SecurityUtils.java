package com.slzh.utils.login;

import com.slzh.config.security.GrantedAuthorityImpl;
import com.slzh.config.security.JwtAuthenticatioToken;
import com.slzh.config.security.JwtUserDetails;
import com.slzh.model.SysUser;

import com.slzh.service.SysUserService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;


import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Security相关操作
 *
 * @author lanb
 * @date Jan 14, 2019
 */
public class SecurityUtils {
    private static final Logger log = LoggerFactory.getLogger(SecurityUtils.class);
    public static Map<String, SysUser> userNameToId = new HashMap<>();
    public static HashSet<String> changeUser = new HashSet<>();
    public static final String PASW = "SHJKFHFKSJHKSH7823468GH766";


    /**
     * 系统登录认证
     *
     * @param request
     * @param username
     * @param password
     * @param authenticationManager
     * @return
     */
    public static JwtAuthenticatioToken login(HttpServletRequest request, String username, String password, AuthenticationManager authenticationManager) {
        JwtAuthenticatioToken token = new JwtAuthenticatioToken(username, password);
        token.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        // 执行登录认证过程
        Authentication authentication = authenticationManager.authenticate(token);
        // 认证成功存储认证信息到上下文
        SecurityContextHolder.getContext().setAuthentication(authentication);
        // 生成令牌并返回给客户端
        token.setToken(JwtTokenUtils.generateToken(authentication));
        return token;
    }

    /**
     * @param request
     * @param username
     * @param authenticationManager
     * @return
     */
    public static JwtAuthenticatioToken changeLogin(HttpServletRequest request, String username, SysUser user, AuthenticationManager authenticationManager) {
        changeUser.clear();
//		if(!"admin".equals(SecurityUtils.getUsername()) && !"admin_dev".equals(SecurityUtils.getUsername()))
//		{
//			return null;
//		}

        //执行token参数设置过程，实际上可以直接用 JwtAuthenticatioToken（extends UsernamePasswordAuthenticationToken）
        // 来做后面的几步动作，但是为了之后流程扩展不混乱，将JwtAuthenticatioToken模拟成官方Authentication
        String lastLoginTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        user.setPassword(PASW);
        user.setSalt(PASW);
        JwtUserDetails userDetails = new JwtUserDetails(user, lastLoginTime, null);
        JwtAuthenticatioToken authToken = new JwtAuthenticatioToken(userDetails, PASW);
        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        Authentication authentication = authToken;

        // 认证成功存储认证信息到上下文
        SecurityContextHolder.getContext().setAuthentication(authentication);
        // 生成令牌并返回给客户端
        JwtAuthenticatioToken token = new JwtAuthenticatioToken(username, PASW);
        token.setToken(JwtTokenUtils.generateToken(authentication));
        token.setDetails(authToken.getDetails());
        changeUser.add(user.getUsername());
        return token;
    }

    public static void updateCacheUserInfo(Long userId, SysUserService sysUserService) {
        SysUser user = sysUserService.findByIdForSecurity(userId);
        SecurityUtils.userNameToId.put(user.getUsername(), user);
    }

    /**
     * 获取令牌进行认证
     *
     * @param request
     */
    public static void checkAuthentication(HttpServletRequest request) {
        // 获取令牌并根据令牌获取登录认证信息
        Authentication authentication = JwtTokenUtils.getAuthenticationeFromToken(request);
        // 设置登录认证信息到上下文
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    /**
     * 获取当前用户名
     *
     * @return
     */
    public static String getUsername() {
        String username = null;
        Authentication authentication = getAuthentication();
        if (authentication != null) {
            Object principal = authentication.getPrincipal();
            if (principal != null && principal instanceof UserDetails) {
                username = ((UserDetails) principal).getUsername();
            } else {
//				log.warn("登录验证的时候获取不到用户名，可能出现403");
            }
        }
        if (StringUtils.isEmpty(username)) {
            log.error("登录验证的时候获取不到用户名，出现null用户名");
        }
        return username;
    }


    /**
     * 获取已经 登录的当前用户
     *
     * @return
     */
    public static Long getUserId(SysUserService sysUserService) {
        String userName = SecurityUtils.getUsername();
        Long userId = -1L;
        try {
            if (SecurityUtils.userNameToId.containsKey(userName)) {
                userId = SecurityUtils.userNameToId.get(userName).getId();
            } else {
                SysUser user = sysUserService.findByNameForSecurity(userName, false);
                userId = user.getId();
                SecurityUtils.userNameToId.put(userName, user);
            }
        } catch (Exception e) {
            log.error("获取userId报错{}", userName);
        }
        return userId;
    }


    /**
     * 获取已经 登录的当前用户名
     *
     * @return
     */
    public static SysUser getUserInfo(SysUserService sysUserService) {
        String userName = SecurityUtils.getUsername();
        SysUser user = null;
        try {
            if (SecurityUtils.userNameToId.containsKey(userName)) {
                user = SecurityUtils.userNameToId.get(userName);
            } else {
                user = sysUserService.findByNameForSecurity(userName, false);
                SecurityUtils.userNameToId.put(userName, user);
            }
        } catch (Exception e) {
            log.error("获取user报错,{}", userName);
        }
        return user;
    }


    /**
     * 微信平台
     * 获取已经 团队Id
     *
     * @return
     */
//    public static Long getTeamIdByWx(String token, WxMapper wxMapper) {
//        SysUser sysUser = wxMapper.findWxUserByToken(token);
//        if (sysUser != null) {
//            return sysUser.getTeamId();
//        }
//        return null;
//    }


    /**
     * 获取未登录的当前用户名
     *
     * @return
     */
    public static Long getUserId(SysUserService sysUserService, String userName) {
        Long userId = -1L;
        try {
            if (SecurityUtils.userNameToId.containsKey(userName)) {
                userId = SecurityUtils.userNameToId.get(userName).getId();
            } else {
                SysUser user = sysUserService.findByNameForSecurity(userName, false);
                userId = user.getId();
                SecurityUtils.userNameToId.put(userName, user);
            }
        } catch (Exception e) {
            log.error("获取userId报错{}", userName);
        }
        return userId;
    }

    public static boolean checkSuperAdmin() {
        return getRoles().contains("SUPER");
    }

    public static boolean checkAdmin() {
        List<String> roles = getRoles();
        return roles.contains("SUPER") || roles.contains("ADMIN");
    }

    /**
     * 获取用户名
     *
     * @return
     */
    public static String getUsername(Authentication authentication) {
        String username = null;
        if (authentication != null) {
            Object principal = authentication.getPrincipal();
            if (principal != null && principal instanceof UserDetails) {
                username = ((UserDetails) principal).getUsername();
            }
        }
        return username;
    }

    /**
     * 获取当前登录信息
     *
     * @return
     */
    public static Authentication getAuthentication() {
        if (SecurityContextHolder.getContext() == null) {
            return null;
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        return authentication;
    }

    static public String refreshToken() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return JwtTokenUtils.generateToken(authentication);
    }

    public static List<String> getRoles() {
        List<GrantedAuthorityImpl> grantedAuthorities = (List<GrantedAuthorityImpl>) SecurityUtils.getAuthentication().getAuthorities();
        List<String> roles = new ArrayList<>();
        for (GrantedAuthorityImpl grantedAuthority : grantedAuthorities) {
            roles.add(grantedAuthority.getAuthority());
        }

        return roles;
    }
}
