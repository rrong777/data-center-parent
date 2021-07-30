package com.slzh.config.security;//package com.slzh.config.security;
//
//
//import com.slzh.dao.SysRoleMapper;
//import com.slzh.model.RolePermissions;
//import com.slzh.utils.login.SecurityUtils;
//import io.jsonwebtoken.lang.Collections;
//import org.apache.commons.lang3.StringUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//import org.springframework.web.context.request.RequestAttributes;
//import org.springframework.web.context.request.RequestContextHolder;
//import org.springframework.web.context.request.ServletRequestAttributes;
//
//import javax.servlet.http.HttpServletRequest;
//import java.util.*;
//import java.util.concurrent.ConcurrentHashMap;
//
//@Component("PermissionService")
//public class PermissionService {
//
//    @Autowired
//    private SysRoleMapper sysRoleMapper;
//
//    private static final Map<String, List<String>> RoleHasPermissions = new ConcurrentHashMap<>();
//
//    public PermissionService() {
//    }
//
//
//
//
//    public Map<String, List<String>> getRoleHasPermissions() {
//        if(!Collections.isEmpty(RoleHasPermissions)) {
//            return RoleHasPermissions;
//        }
//        synchronized (RoleHasPermissions) {
//            if(!Collections.isEmpty(RoleHasPermissions)) {
//                return RoleHasPermissions;
//            }
//            setRoleHasPermissions();
//            return RoleHasPermissions;
//        }
//    }
//
//    private HttpServletRequest getRequest() {
//        // 获得HTTP请求对象
//        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
//        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) requestAttributes;
//        HttpServletRequest request = servletRequestAttributes.getRequest();
//        return request;
//    }
//
//    public boolean judgeHaveAccessPermissaionOrNot(HttpServletRequest request) {
//        if(request == null) {
//            request = getRequest();
//        }
//        String uri = request.getRequestURI();
//        List<String> userRoles = SecurityUtils.getRoles();
//        if(Collections.isEmpty(userRoles)) {
//            return false;
//        }
//        if(userRoles.contains("ADMIN") || userRoles.contains("SUPER")) {
//            return true;
//        }
//        Map<String, List<String>> rolePermissions = getRoleHasPermissions();
//        for(String role : userRoles) {
//            List<String> roleCouldAccessUri = rolePermissions.get(role);
//            if (Collections.isEmpty(roleCouldAccessUri)) {
//                return false;
//            }
//            if(roleCouldAccessUri.contains(uri)) {
//                return true;
//            }
//        }
//        return false;
//    }
//
//
//}
