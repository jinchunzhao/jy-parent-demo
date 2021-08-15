package com.jy.common.config.auth;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 认证token自动配置
 *
 * @author jinchunzhao
 * @version 1.0
 * @date 2021-08-14 21:00
 */
@Configuration
public class TokenConfig {
    @Bean
    public TokenDecode tokenDecode(){
        return new TokenDecode();
    }
}
