package com.icy9.token;

import com.icy9.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collection;

/**
 * 实现AuthenticationProvider 用于验证token
 * Created by f00lish on 2017/7/4.
 * Qun:530350843
 */
public class TokenAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private TokenUserDetailsService tokenUserDetailsService;
    /**
     * 自定义验证方式
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = (String) authentication.getCredentials();
        TokenUserDetails user = (TokenUserDetails) tokenUserDetailsService.loadUserByUsername(username);
//        TokenUserDetails user = new TokenUserDetails(new User());
        if(user == null || user.getUser() == null){
            throw new BadCredentialsException("Username not found.");
        }
        if (user.getPassword() == null)
        {
            throw new BadCredentialsException("Wrong not found.");
        }
        //加密过程在这里体现
        //加密密码
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(10);

        if (!bCryptPasswordEncoder.matches(password,user.getPassword())) {
            throw new BadCredentialsException("Wrong password.");
        }

        Collection<? extends GrantedAuthority> authorities = user.getAuthorities();
        return new UsernamePasswordAuthenticationToken(user, password, authorities);
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}
