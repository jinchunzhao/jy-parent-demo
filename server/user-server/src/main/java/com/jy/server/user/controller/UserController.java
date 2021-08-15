package com.jy.server.user.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.jy.common.pojo.User;
import com.jy.common.web.ResultBean;
import com.jy.server.user.service.UserService;
import com.netflix.discovery.converters.Auto;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

import javax.validation.Valid;

/**
 * 用户控制controller
 *
 * @author jinchunzhao
 * @version 1.0
 * @date 2021-08-14 10:18
 */
@RestController
@Slf4j
@RequestMapping("/user")
@Api(value = "平台用户控制器", tags = {"平台用户控制器相关接口"})
public class UserController {

    @Auto
    private UserService userService;

    /**
     * 根据用户名获取用户信息
     *
     * @param username
     *        用户名
     * @return
     *        用户信息
     */
    @ResponseBody
    @GetMapping("/load/{username}")
    @ApiOperation(value = "根据用户名获取用户信息", notes = "根据用户名获取用户信息")
    public ResultBean<User> findUserInfo(@PathVariable("username") String username){

        return ResultBean.success(userService.findUserInfo(username));
    }


    /**
     * 根据用户名获取用户信息
     *
     * @param user
     *        用户
     * @return
     *        信息
     */
    @ResponseBody
    @PostMapping("/register")
    @ApiOperation(value = "根据用户名获取用户信息", notes = "根据用户名获取用户信息")
    public ResultBean<User> userRegister(@RequestBody @Valid User user){
        userService.userRegister(user);
        return ResultBean.success();
    }

}
