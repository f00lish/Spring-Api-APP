package com.icy9;

import com.icy9.token.TokenAuthenticationEntryPoint;
import com.icy9.token.TokenAuthenticationProvider;
import com.icy9.token.TokenAuthenticationTokenFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Created by f00lish on 2017/7/4.
 * Qun:530350843
 */
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * 对API访问路径进行一些权限设置
     * 如果需要认证才能访问的api，路径要为/api/auth/
    */
    @Configuration
    @Order(1)
    public static class ApiSecurityAdapter extends WebSecurityConfigurerAdapter {
        protected void configure(HttpSecurity http) throws Exception {
            http.antMatcher("/api/auth/**")
                    .authorizeRequests().anyRequest().hasRole("USER").and().csrf().disable();
//             添加JWT filter
            http.addFilterBefore(authenticationTokenFilterBean(), UsernamePasswordAuthenticationFilter.class)
                    .exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint());
        }
        @Bean
        public TokenAuthenticationTokenFilter authenticationTokenFilterBean() throws Exception {
            return new TokenAuthenticationTokenFilter();
        }
        @Bean
        public TokenAuthenticationEntryPoint jwtAuthenticationEntryPoint() throws Exception {
            return new TokenAuthenticationEntryPoint();
        }
    }

    /**
     * 配置无需验证的路径
     *
     */
    @Configuration
    @Order(2)
    public static class NoSecurityAdapter extends WebSecurityConfigurerAdapter {

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.authorizeRequests()
                    .anyRequest().permitAll().and().csrf().disable();
        }
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(customAuthenticationProvider());
    }
    @Bean
    public TokenAuthenticationProvider customAuthenticationProvider() throws Exception {
        return new TokenAuthenticationProvider();
    }

}
