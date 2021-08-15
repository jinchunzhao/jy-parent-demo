package com.jy.server.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jy.common.exception.CastException;
import com.jy.common.pojo.User;
import com.jy.common.web.ResultBeanCode;
import com.jy.server.user.dao.UserDao;
import com.jy.server.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Objects;

/**
 * 用户服务接口实现类
 *
 * @author jinchunzhao
 * @version 1.0
 * @date 2021-08-14 16:02
 */
@Slf4j
@Service
public class UserServiceImpl extends ServiceImpl<UserDao, User> implements UserService {

    @Override
    public User findUserInfo(String username) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUserName,username);
        return baseMapper.selectOne(wrapper);
    }

    @Override
    public void userRegister(User user) {
        String userName = user.getUserName();
        User userInfo = this.findUserInfo(userName);
        if (Objects.nonNull(userInfo)){
            CastException.cast(ResultBeanCode.USER_EXIST);
        }

        user.setRegTime(new Date());
        this.baseMapper.insert(user);
    }
}
