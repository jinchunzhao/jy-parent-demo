package com.jy.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * 服务网关
 *
 * @author JinChunZhao
 * @version 1.0
 * @date 2021-08-08 16:08
 */
@EnableDiscoveryClient
@EnableEurekaClient
@SpringBootApplication
public class GatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }


    /**
     * 创建用户唯一标识，使用IP作为用户的唯一标识，来根据IP进行限流操作
     *
     * @return
     */
    @Bean
    public KeyResolver useKeyResolver(){
        return new KeyResolver() {
            @Override
            public Mono<String> resolve(ServerWebExchange exchange) {
                //return Mono.just("需要使用的用户身份识别唯一标识【ip】")
                String ip = exchange.getRequest().getRemoteAddress().getHostString();

                return Mono.just(ip);
            }
        };
    }
}
