package com.jy.server.order.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import com.jy.common.utils.auth.JksUtils;

/**
 * 资源服务器授权配置，当前资源服务器对用户权限的校验
 *
 * 注解@EnableResourceServer 开启资源校验服务，令牌校验
 * 注解@EnableGlobalMethodSecurity全局的方法校验，开启注解的支持激活方法上的PreAuthorize注解
 *
 * @author jinchunzhao
 * @version 1.0
 * @date 2021-08-15 15:43
 */
@Configuration
@EnableResourceServer
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true) // 激活方法上的PreAuthorize注解
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    @Value("${encrypt.public-key}")
    private String publicKey;

    /***
     * 定义JwtTokenStore
     * 
     * @param jwtAccessTokenConverter
     * @return
     */
    @Bean
    public TokenStore tokenStore(JwtAccessTokenConverter jwtAccessTokenConverter) {
        return new JwtTokenStore(jwtAccessTokenConverter);
    }

    /***
     * 定义JJwtAccessTokenConverter
     * 
     * @return
     */
    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setVerifierKey(JksUtils.getPubKey(publicKey));
        return converter;
    }

    /***
     * Http安全配置，对每个到达系统的http请求链接进行校验
     * 
     * @param http
     * @throws Exception
     */
    @Override
    public void configure(HttpSecurity http) throws Exception {
        // 所有请求必须认证通过
        http.authorizeRequests()
            // //下边的路径放行
            // .antMatchers(
            // "/user/add","/user/load/**"). //配置地址放行
            // permitAll()
            .anyRequest().authenticated(); // 其他地址需要认证授权
    }
}
