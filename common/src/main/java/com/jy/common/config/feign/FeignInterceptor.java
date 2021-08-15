package com.jy.common.config.feign;

import com.jy.common.constants.SsoConstant;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.Objects;

/**
 * 自定义feign拦截器,并将所有头文件数据再次加入到Feign请求的微服务头文件中
 * <p>
 * feign调用之前执行拦截
 * </p>
 *
 * @author jinchunzhao
 * @version 1.0
 * @date 2021-08-14 15:51
 */
public class FeignInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate requestTemplate) {
        // 传递令牌
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes != null) {
            // 记录了当前用户请求的所有数据，包括请求头和请求参数，这里是指当前请求的时候对应线程的数据
            // 如果开启了熔断，默认是线程池隔离，会开启新的线程，需要将熔断策略换成信号量隔离，此时不会开启新的线程
            HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
            if (request != null) {
                Enumeration<String> headerNames = request.getHeaderNames();
                while (headerNames.hasMoreElements()) {
                    String headerName = headerNames.nextElement();
                    if (Objects.equals(SsoConstant.AUTH.toLowerCase(), headerName)) {
                        // Bearer jwt
                        String headerValue = request.getHeader(headerName);

                        // 传递令牌,使用feign调用的时候，传递给下一个微服务
                        // 各大微服务之间的认证就是令牌的传递
                        requestTemplate.header(headerName, headerValue);
                    }
                }
            }
        }
    }

}
