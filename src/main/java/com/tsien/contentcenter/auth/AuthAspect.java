package com.tsien.contentcenter.auth;

import com.tsien.contentcenter.util.JwtOperator;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Objects;

/**
 * Created with IntelliJ IDEA.
 *
 * @author tsien
 * @version 1.0.0
 * @date 2020/9/26 0026 13:17
 */

@Aspect
@Component
@Slf4j
public class AuthAspect {

    @Resource
    private JwtOperator jwtOperator;

    @Around("@annotation(com.tsien.contentcenter.auth.CheckLogin)")
    public Object checkLogin(ProceedingJoinPoint point) throws Throwable {
        checkToken();
        return point.proceed();

    }

    @Around("@annotation(com.tsien.contentcenter.auth.CheckAuthorization)")
    public Object checkAuthorization(ProceedingJoinPoint point) throws Throwable {

        try {
            // 1. 验证token是否合法
            checkToken();

            // 2.验证用户角色是否匹配
            HttpServletRequest request = getHttpServletRequest();
            String role = (String) request.getAttribute("role");

            MethodSignature signature = (MethodSignature) point.getSignature();
            Method method = signature.getMethod();
            CheckAuthorization annotation = method.getAnnotation(CheckAuthorization.class);

            String value = annotation.value();

            if (!Objects.equals(value, role)) {
                throw new SecurityException("用户无权访问");
            }


        } catch (Throwable throwable) {
            throw new SecurityException("用户无权访问", throwable);
        }
        return point.proceed();
    }

    private void checkToken() {
        HttpServletRequest request = getHttpServletRequest();

        // 2.校验token是否合法、过期，如果不合法，直接抛异常，如果合法就放行
        String token = request.getHeader("X-Token");
        Boolean isValid = jwtOperator.validateToken(token);

        if (!isValid) {
            throw new SecurityException("Token不合法！");
        }

        // 3.如果校验成功，那么将用户的信息设置到request的attribute里面
        Claims claims = jwtOperator.getClaimsFromToken(token);
        request.setAttribute("id", claims.get("id"));
        request.setAttribute("wxNickname", claims.get("wxNickname"));
        request.setAttribute("role", claims.get("role"));
    }

    private HttpServletRequest getHttpServletRequest() {
        // 1.从header里获取token
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes attributes = (ServletRequestAttributes) requestAttributes;
        if (attributes == null) {
            throw new SecurityException("request信息为空");
        }
        return attributes.getRequest();
    }

}
