package com.slzh.config.security;

import com.slzh.model.SysUser;
import com.slzh.service.SysUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


/**
 * 用户登录认证信息查询
 * 会出现无问题大概率是这个地方问题
 * @author lanb
 * @date Jan 14, 2019
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private static final Logger log = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

    @Autowired
    private SysUserService sysUserService;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            log.info("准备获取用户登录信息,当前用户" + username);
            SysUser user = sysUserService.findByNameForSecurity(username, true);
            if (user == null) {
                log.error("该用户不存在" + username);
                throw new UsernameNotFoundException("该用户不存在");
            }
            List<String> permissions = new ArrayList<>();
            if(user.getRoles() != null) {
                permissions = Arrays.asList(user.getRoles().split(",").clone());
            }

//            List<String> permissions = new ArrayList<>();

            if (permissions == null) {
                log.error("该用户菜单不存在" + username);
            }

            List<GrantedAuthority> grantedAuthorities = permissions.stream().map(GrantedAuthorityImpl::new).collect(Collectors.toList());
            log.info("获取用户登录信息成功，开始封装UserDetails,当前用户" + username);
            String lastLoginTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            return new JwtUserDetails(user, lastLoginTime, grantedAuthorities);

        }catch (UsernameNotFoundException e1){
            throw e1;
        }catch (Exception e2){
            log.error("登录认证失败，出问题了，请排查,",e2);
            throw e2;
        }
    }
}