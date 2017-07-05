package com.icy9.encryption;

import com.icy9.util.other_util.CommonUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdvice;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;

/**
 * Created by f00lish on 2017/7/5.
 * Qun:530350843
 * 只会拦截post请求中带body的那种
 */
@Order(1)
@ControllerAdvice(basePackages = "com.icy9.controller")
public class HrqRequestBodyAdvice implements RequestBodyAdvice{

    @Value("${config.settings.interfaceEncode}")
    private boolean encrySettings;

    //包含项
    private String[] includes = {};
    //排除项
    private String[] excludes = {};
    //是否加密
    private boolean encode = true;

    @Override
    public boolean supports(MethodParameter methodParameter, Type type, Class<? extends HttpMessageConverter<?>> aClass) {
        //这里可以根据自己的需求
        return true;
    }

    @Override
    public Object handleEmptyBody(Object o, HttpInputMessage httpInputMessage, MethodParameter methodParameter, Type type, Class<? extends HttpMessageConverter<?>> aClass) {
        return o;
    }

    @Override
    public HttpInputMessage beforeBodyRead(HttpInputMessage httpInputMessage, MethodParameter methodParameter, Type type, Class<? extends HttpMessageConverter<?>> aClass) throws IOException {
        final HttpInputMessage http = httpInputMessage;
        //重新初始化为默认值
        includes = new String[]{};
        excludes = new String[]{};
        encode = encrySettings;//全局加密设置，如果不用改为false
        if(methodParameter.getMethod().isAnnotationPresent(SerializedField.class)){
            //获取注解配置的包含和去除字段
            SerializedField kserialized = methodParameter.getMethodAnnotation(SerializedField.class);
            //是否加密
            encode = kserialized.encode();
            includes = kserialized.includes();
            excludes = kserialized.excludes();
        }
        HttpInputMessage message = new HttpInputMessage() {
            @Override
            public InputStream getBody() throws IOException {
                String str = CommonUtil.convertStreamToString(http.getBody());
                String json = str;
                if (encode)
                {
                    json = DesHelper.decode(str);
                }
                InputStream in_withcode = new ByteArrayInputStream(json.getBytes("UTF-8"));
                return in_withcode;
            }

            @Override
            public HttpHeaders getHeaders() {
                return http.getHeaders();
            }
        };

        return message;
    }
    @Override
    public Object afterBodyRead(Object o, HttpInputMessage httpInputMessage, MethodParameter methodParameter, Type type, Class<? extends HttpMessageConverter<?>> aClass) {
        return o;
    }
}
