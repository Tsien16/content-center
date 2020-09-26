package com.tsien.contentcenter.feignclient.interceptor;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * Created with IntelliJ IDEA.
 *
 * @author tsien
 * @version 1.0.0
 * @date 2020/9/26 0026 16:34
 */

public class TokenRelayRequestInterceptor implements RequestInterceptor {

    /**
     * Called for every request. Add data using methods on the supplied {@link RequestTemplate}.
     *
     * @param template template
     */
    @Override
    public void apply(RequestTemplate template) {

        // 1.获取token
        // 1.从header里获取token
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();

        ServletRequestAttributes attributes = (ServletRequestAttributes) requestAttributes;

        if (attributes == null) {
            throw new SecurityException("request信息为空");
        }
        HttpServletRequest request = attributes.getRequest();
        String token = request.getHeader("X-Token");

        // 2.将token传递
        if (StringUtils.isNotBlank(token)) {
            template.header("X-Token", token);
        }
    }
}
