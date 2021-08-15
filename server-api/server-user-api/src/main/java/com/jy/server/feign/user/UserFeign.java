package com.jy.server.feign.user;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.jy.common.pojo.User;
import com.jy.common.web.ResultBean;
import com.jy.server.feign.user.fallback.UserFeignFallBack;

/**
 * user feign远程调用
 * 
 * @author jinchunzhao
 * @version 1.0
 * @date 2021-08-14 13:22
 */
@FeignClient(name = "user",fallback = UserFeignFallBack.class)
public interface UserFeign {


    /**
     * 根据用户名获取用户信息
     * 
     * @param username
     *        用户名
     * @return
     *        用户信息
     */
    @GetMapping("/user/load/{username}")
    public ResultBean<User> findUserInfo(@PathVariable("username") String username);
}
