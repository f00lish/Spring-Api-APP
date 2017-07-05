package com.icy9;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * 配置
 * Created by f00lish on 2017/7/4.
 * Qun:530350843
 */
@Configuration
public class WebMvcConfigurer extends WebMvcConfigurerAdapter {

    //可以进行urlMapping地址转换
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        super.addViewControllers(registry);
        registry.addViewController("/").setViewName("index");
    }
}
