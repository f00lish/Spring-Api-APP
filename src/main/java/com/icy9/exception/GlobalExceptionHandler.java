package com.icy9.exception;

import com.icy9.entity.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by f00lish on 2017/7/5.
 * Qun:530350843
 * 全局的异常处理
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ResponseEntity<?> defaultErrorHandler(HttpServletRequest req, Exception e) throws Exception {
        Response response = new Response();
        response.setMsg(Response.FAIL,"访问出错",e.toString());
        return ResponseEntity.ok(response);
    }
}