package com.jy.gateway.config.filter;

import java.util.List;
import java.util.Objects;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpCookie;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import com.jy.common.constants.SsoConstant;
import com.jy.common.utils.auth.AuthService;
import com.jy.common.utils.auth.JwtUtil;
import com.jy.gateway.config.properties.IgnoreAuthPathProperties;

import io.jsonwebtoken.Claims;
import reactor.core.publisher.Mono;

/**
 * 安全认证过滤器
 *
 * @author JinChunZhao
 * @version 1.0
 * @date 2021-08-08 10:29
 */
@Component
public class AuthFilter implements GlobalFilter, Ordered {

    @Autowired
    private IgnoreAuthPathProperties ignoreAuthPathProperties;

    @Autowired
    private AuthService authService;

    @Override
    public int getOrder() {
        return 0;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();

        String path = request.getURI().getPath();

        List<String> ignoreAuthUrls = ignoreAuthPathProperties.getIgnoreAuthUrls();
        // 1.判断当前请求路径是否为不需要认证的路径,如果是,则直接放行
        if (CollectionUtils.isNotEmpty(ignoreAuthUrls)) {
            for (int i = 0; i < ignoreAuthUrls.size(); i++) {
                String ignoreUrl = ignoreAuthUrls.get(i);
                if (FilenameUtils.wildcardMatch(path, ignoreUrl)) {
                    // 放行
                    return chain.filter(exchange);
                }
            }
        }

        // 获取当前的请求头信息
        HttpHeaders headers = request.getHeaders();
        // 获取令牌信息
        String jwtToken = headers.getFirst(SsoConstant.AUTH);
        // true：令牌在头文件中， false：令牌不在头文件中，将令牌封装到头文件中，再传递给其他微服务
        boolean hasToken = true;

        if (StringUtils.isBlank(jwtToken)) {
            jwtToken = request.getQueryParams().getFirst(SsoConstant.AUTH);
            hasToken = false;
        }
        // 从cookie中获取令牌
        if (StringUtils.isBlank(jwtToken)) {
            HttpCookie httpCookie = request.getCookies().getFirst(SsoConstant.AUTH);
            if (Objects.nonNull(httpCookie)) {
                jwtToken = httpCookie.getValue();
            }
        }
        // 如果没有令牌，则进行拦截
        if (StringUtils.isBlank(jwtToken)) {
            // 设置拦截权限状态码 401
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            // 响应空数据
            return response.setComplete();
        }
        // 如果有令牌，则校验令牌是否有效
        try {
            Claims claims = JwtUtil.parseJWT(jwtToken);
        } catch (Exception e) {
            // 无效则拦截
            // 设置拦截权限状态码 401
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            // 响应空数据
            return response.setComplete();
        }

        // 从cookie中获取jti的值,如果该值不存在,拒绝本次访问
        String jti = authService.getJtiFromCookie(request);
        if (StringUtils.isEmpty(jti)) {
            // 拒绝访问
            // 设置拦截权限状态码 401
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            // 响应空数据
            return response.setComplete();
        }

        // 3.从redis中获取jwt的值,如果该值不存在,拒绝本次访问
        String jwt = authService.getTokenFromRedis(jti);
        if (StringUtils.isEmpty(jwt)) {
            // 拒绝访问
            // 设置拦截权限状态码 401
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            // 响应空数据
            return response.setComplete();
        }

        // 4.对当前的请求对象进行增强,让它会携带令牌的信息
        request.mutate().header(SsoConstant.AUTH, SsoConstant.BEARER + jwt);
        return chain.filter(exchange);
    }

    // //跳转登录页面
    // private Mono<Void> toLoginPage(String loginUrl, ServerWebExchange exchange) {
    // ServerHttpResponse response = exchange.getResponse();
    // response.setStatusCode(HttpStatus.SEE_OTHER);
    // response.getHeaders().set("Location","loginUrl");
    // return response.setComplete();
    // }
}