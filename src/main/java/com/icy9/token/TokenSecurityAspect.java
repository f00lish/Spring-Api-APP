package com.icy9.token;

import com.icy9.entity.User;
import com.icy9.service.UserService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

/**
 * Created by f00lish on 2017/7/7.
 * Qun:530350843
 * Token验证,面向切面
 */
@Aspect
@Component
public class TokenSecurityAspect {

    private static final Logger LOGGER = LoggerFactory.getLogger(TokenSecurityAspect.class);

    @Autowired
    private TokenUtil tokenUtil;

    @Value("${jwt.header}")
    private String tokenHeader;

    @Value("${jwt.tokenHead}")
    private String tokenHead;

    @Autowired
    private UserService userService;

    @Pointcut("@annotation(org.springframework.web.bind.annotation.RequestMapping)")
    public void controllerAspect() {

    }

    /**
     * 接收到客户端请求时执行
     *
     * @param pjp
     * @return
     * @throws Throwable
     */
    @Around("controllerAspect()")
    public Object execute(ProceedingJoinPoint pjp) throws Throwable {
        // 从切点上获取目标方法
        MethodSignature methodSignature = (MethodSignature) pjp.getSignature();
        Method method = methodSignature.getMethod();
        /**
         * 验证Token
         */
        if (method.isAnnotationPresent(TokenValidate.class)) {
            // 从 request header 中获取当前 token
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            String authHeader = request.getHeader(this.tokenHeader);
            if (authHeader != null && authHeader.startsWith(tokenHead)) {
                final String authToken = authHeader.substring(tokenHead.length()); // The part after "Bearer "
                String username = tokenUtil.getUsernameFromToken(authToken);
                LOGGER.info("checking authentication " + username);
                if (username != null ) {
                    User userDetails = this.userService.getUserByUsername(username);

                    if (!tokenUtil.validateToken(authToken, userDetails)) {
                        String message = String.format("Token [%s] 无效", authHeader);
                        throw new TokenException(message);
                    }
                }else {
                    String message = String.format("Token [%s] 无效", authHeader);
                    throw new TokenException(message);
                }
            }else
            {
                throw new TokenException("Token不能为空");
            }
        }
        // 调用目标方法
        return pjp.proceed();
    }
}
