
package com.jy.gateway.config.cors;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import org.springframework.web.util.pattern.PathPatternParser;

/**
 * cors跨域配置 gateway使用的是webflux，而不是springmvc，所以需要先关闭webflux的cors，再从gateway的filter里边设置cors就行了。 注意： IE则不能低于IE10
 *
 * @author jinchunzhao
 * @version 1.0
 * @date 2020-04-03 9:40
 */
@Configuration
public class CorsConfig {

    private static final Long MAX_AGE = 18000L;

    /**
     * attention: 简单跨域就是GET，HEAD和POST请求， 但是POST请求 的"Content-Type"只能是application/x-www-form-urlencoded,
     * multipart/form-data 或 text/plain 反之，就是非简单跨域，此跨域有一个预检机制：就是会发两次请求，一次OPTIONS请求，一次真正的请求
     *
     * @return CorsWebFilter实例
     */
    @Bean
    public CorsWebFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();
        // 允许cookies跨域
        config.setAllowCredentials(true);
        // #允许向该服务器提交请求的URI，*表示全部允许，在SpringMVC中，如果设成*，会自动转成当前请求头中的Origin
        config.addAllowedOrigin("*");
        // #允许访问的头信息,*表示全部--可根据项目需求添加自定义的请求头例如： token...
        config.addAllowedHeader("*");
        // 预检请求的缓存时间（秒），即在这个时间段里，对于相同的跨域请求不会再预检
        config.setMaxAge(MAX_AGE);
        // 允许提交请求的方法类型，*表示全部允许
        // OPTIONS：预检请求
        config.addAllowedMethod("OPTIONS");
        config.addAllowedMethod("HEAD");
        config.addAllowedMethod("GET");
        config.addAllowedMethod("PUT");
        config.addAllowedMethod("POST");
        config.addAllowedMethod("DELETE");
        config.addAllowedMethod("PATCH");

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource(new PathPatternParser());
        source.registerCorsConfiguration("/**", config);

        return new CorsWebFilter(source);
    }
}