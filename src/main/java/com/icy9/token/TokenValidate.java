package com.icy9.token;

import java.lang.annotation.*;

/**
 * Created by f00lish on 2017/7/7.
 * Qun:530350843
 * Token注解校验
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface TokenValidate {
}
