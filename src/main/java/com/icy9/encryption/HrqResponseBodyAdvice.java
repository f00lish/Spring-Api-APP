package com.icy9.encryption;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * Created by f00lish on 2017/7/5.
 * Qun:530350843
 */
@Order(1)
@ControllerAdvice(basePackages = "com.icy9.controller")
public class HrqResponseBodyAdvice implements ResponseBodyAdvice {


    @Value("${config.settings.interfaceEncode}")
    private boolean encrySettings;

    //包含项
    private String[] includes = {};
    //排除项
    private String[] excludes = {};
    //是否加密
    private boolean encode = true;

    @Override
    public boolean supports(MethodParameter methodParameter, Class aClass) {
        //这里可以根据自己的需求
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object o, MethodParameter methodParameter, MediaType mediaType, Class aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        //重新初始化为默认值
        includes = new String[]{};
        excludes = new String[]{};
        encode = encrySettings;//全局加密设置，如果不用改为false
        //判断返回的对象是单个对象，还是list，活着是map
        if(o==null){
            return null;
        }
        if(methodParameter.getMethod().isAnnotationPresent(SerializedField.class)){
            //获取注解配置的包含和去除字段
            SerializedField serializedField = methodParameter.getMethodAnnotation(SerializedField.class);
            includes = serializedField.includes();
            //是否加密
            encode = serializedField.encode();
            excludes = serializedField.excludes();
        }
        //使用Jackson的ObjectMapper将Java对象转换成Json String
        ObjectMapper objectWriter = new ObjectMapper();
        String json = null;
        try {
            json = objectWriter.writeValueAsString(o);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        Object retobj = o;
        //加密
        if (encode)
        {
            retobj = DesHelper.encode(json);
        }
        //输出
        return retobj;

    }
}
