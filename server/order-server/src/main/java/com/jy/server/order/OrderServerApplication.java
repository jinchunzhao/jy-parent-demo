package com.jy.server.order;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

import com.jy.common.config.feign.FeignInterceptor;
import org.springframework.context.annotation.Bean;

/**
 * 订单服务启动类
 * 
 * @author jinchunzhao
 * @version 1.0
 * @date 2021-08-14 21:03
 */
@SpringBootApplication
@EnableEurekaClient
@MapperScan(basePackages = {"com.jy.**.dao"})
@EnableFeignClients(basePackages = {"com.jy.server.feign.user"})
public class OrderServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrderServerApplication.class, args);
    }


    @Bean
    public FeignInterceptor feignInterceptor(){
        return new FeignInterceptor();
    }
}
