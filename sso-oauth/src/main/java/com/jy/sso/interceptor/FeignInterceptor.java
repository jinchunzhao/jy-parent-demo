package com.jy.sso.interceptor;

import com.jy.common.constants.SsoConstant;
import com.jy.sso.config.auth.AdminAuthTokenService;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * 自定义feign拦截器,并将所有头文件数据再次加入到Feign请求的微服务头文件中
 *
 * <p>
 *     feign调用之前执行拦截
 * </p>
 *
 * @author jinchunzhao
 * @version 1.0
 * @date 2021-08-14 15:51
 */
@Component
public class FeignInterceptor implements RequestInterceptor {

    @Autowired
    private AdminAuthTokenService adminAuthTokenService;

    @Override
    public void apply(RequestTemplate requestTemplate) {
        //传递令牌,使用feign调用的时候，传递给下一个微服务
        //各大微服务之间的认证就是令牌的传递
        String adminAuthToken = adminAuthTokenService.getAdminAuthToken(new String[]{"admin", "auth"});
        requestTemplate.header(SsoConstant.AUTH,SsoConstant.BEARER+adminAuthToken);

    }
}
