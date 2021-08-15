package com.jy.sso;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;


/**
 * sso 启动类
 *
 * @author JinChunZhao
 * @version 1.0
 * @date 2021-08-10 16:15
 */
@EnableDiscoveryClient
@EnableFeignClients(basePackages = {"com.jy.server.feign.user"})
@SpringBootApplication
public class SsoOauthApplication {

    public static void main(String[] args) {
        SpringApplication.run(SsoOauthApplication.class, args);
    }

    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}
