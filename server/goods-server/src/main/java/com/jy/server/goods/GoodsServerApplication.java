package com.jy.server.goods;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * 商品服务启动类
 * 
 * @author jinchunzhao
 * @version 1.0
 * @date 2021-08-15 13:40
 */
@SpringBootApplication
@EnableEurekaClient
@MapperScan(basePackages = {"com.jy.**.dao"})
public class GoodsServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(GoodsServerApplication.class, args);
    }

}
