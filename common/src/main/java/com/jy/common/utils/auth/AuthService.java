package com.jy.common.utils.auth;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpCookie;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Service;

import com.jy.common.constants.SsoConstant;


@Service
public class AuthService {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 判断cookie中jti是否存在
     * @param request
     * @return
     */
    public String getJtiFromCookie(ServerHttpRequest request) {

        HttpCookie cookie = request.getCookies().getFirst("uid");
        if (cookie!=null){
            return cookie.getValue();
        }
        return null;
    }

    /**
     * 判断redis中令牌是否过期
     * @param jti
     *        jwt唯一标识
     * @return
     */
    public String getTokenFromRedis(String jti) {
        String key = SsoConstant.SSO + jti;
        return stringRedisTemplate.boundValueOps(key).get();
    }
}