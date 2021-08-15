package com.jy.server.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jy.common.pojo.User;

/**
 * 用户相关service
 *
 * @author jinchunzhao
 * @version 1.0
 * @date 2021-08-14 16:00
 */
public interface UserService extends IService<User>{

    /**
     * 根据用户名查询用户信息
     *
     * @param username
     *        用户名
     * @return
     *        用户信息
     */
    User findUserInfo(String username);

    /**
     * 用户注册
     *
     * @param user
     *        参数信息
     */
    void userRegister(User user);
}
