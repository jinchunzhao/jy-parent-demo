package com.jy.server.user.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jy.common.pojo.User;
import org.springframework.stereotype.Repository;

/**
 * 用户dao
 * 
 * @author jinchunzhao
 * @version 1.0
 * @date 2021-08-14 11:43
 */
@Repository
public interface UserDao extends BaseMapper<User> {
}
