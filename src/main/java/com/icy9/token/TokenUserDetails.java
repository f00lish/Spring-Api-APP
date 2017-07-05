package com.icy9.token;

import com.icy9.entity.User;
import com.icy9.entity.UserAuthority;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * Created by f00lish on 2017/7/4.
 * Qun:530350843
 */
public class TokenUserDetails implements UserDetails{

    private User user;

    public TokenUserDetails(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getLastPasswordResetDate() {
        if (user == null)
            return null;
        return user.getLastPasswordResetDate();
    }

//    public void setLastPasswordResetDate(Date getLastPasswordResetDate) {
//        this.lastPasswordResetDate = getLastPasswordResetDate;
//    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        //        定义权限集合
        List<SimpleGrantedAuthority> grantedAuthorities = new ArrayList<>();
        //添加权限到集合中
        if (user != null)
        {
            List<UserAuthority> roles =  user.getRoles();
            for (UserAuthority role : roles){
                grantedAuthorities.add(new SimpleGrantedAuthority(role.getAuthority()));
            }
            return grantedAuthorities;
        }
        return null;

    }

    @Override
    public String getPassword() {
        if (user == null)
            return null;
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        if (user == null)
            return null;
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
