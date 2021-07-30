package com.slzh.config.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.slzh.model.SysUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

/**
 * 安全用户模型
 * @author lanb
 * @date Jan 14, 2019
 */
public class JwtUserDetails implements UserDetails {

	private static final long serialVersionUID = 1L;
	
	private String username;
    private String password;
    private String salt;
    private Collection<? extends GrantedAuthority> authorities;
    private String policeId;
    private String lastLoginTime;
    private String nickname;
    private String mobile;
    private List<String> role;
    private String organization;
    private String idCard;

    public JwtUserDetails(SysUser sysUser, String lastLoginTime, Collection<? extends GrantedAuthority> authorities) {
        this.username = sysUser.getUsername();
        this.password = sysUser.getPassword();
        this.salt = sysUser.getSalt();
        this.authorities = authorities;
        this.policeId = sysUser.getPoliceId();
        this.lastLoginTime = lastLoginTime;
        this.mobile = sysUser.getMobile();
        this.nickname = sysUser.getNickname();
        this.idCard = sysUser.getIdCard();
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public List<String> getRole() {
        return role;
    }

    public void setRole(List<String> role) {
        this.role = role;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(String lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getPoliceId() {
        return policeId;
    }

    public void setPoliceId(String policeId) {
        this.policeId = policeId;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @JsonIgnore
    @Override
    public String getPassword() {
        return password;
    }
    @JsonIgnore
    public String getSalt() {
		return salt;
	}
    
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isEnabled() {
        return true;
    }
}