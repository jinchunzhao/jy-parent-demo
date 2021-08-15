package com.jy.server.feign.user.fallback;


import com.jy.common.pojo.User;
import com.jy.common.web.ResultBean;
import com.jy.common.web.ResultBeanCode;
import org.springframework.stereotype.Component;

import com.jy.server.feign.user.UserFeign;

/**
 * user feign远程调用服务降级
 *
 * @author jinchunzhao
 * @version 1.0
 * @date 2021-08-14 13:27
 */
@Component
public class UserFeignFallBack extends CommonFallBackServer implements UserFeign {


    @Override
    public ResultBean<User> findUserInfo(String username) {
        return new ResultBean<User>(ResultBeanCode.ERROR.getCode(),"服务异常进入降级处理");
    }
}
