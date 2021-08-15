package com.jy.common.config.auth;

import cn.hutool.json.JSONUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.jwt.crypto.sign.RsaVerifier;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 解析token
 * 
 * @author jinchunzhao
 * @version 1.0
 * @date 2021-08-14 17:07
 */
public class TokenDecode {

    @Value("encrypt.public-key")
    private String publicKey;

    // //公钥
    // private static final String PUBLIC_KEY = "public.key";

    private static String publicKeyValue = "";

    /***
     * 获取用户信息
     * 
     * @return
     */
    public Map<String, String> getUserInfo() {
        // 获取授权信息
        OAuth2AuthenticationDetails details =
            (OAuth2AuthenticationDetails) SecurityContextHolder.getContext().getAuthentication().getDetails();
        // 令牌解码
        return dcodeToken(details.getTokenValue());
    }

    /***
     * 读取令牌数据
     */
    public Map<String, String> dcodeToken(String token) {
        // 校验Jwt
        Jwt jwt = JwtHelper.decodeAndVerify(token, new RsaVerifier(getPubKey()));

        // 获取Jwt原始内容
        String claims = jwt.getClaims();

        return JSONUtil.toBean(claims, Map.class);
    }

    /**
     * 获取非对称加密公钥 Key
     * 
     * @return 公钥 Key
     */
    public String getPubKey() {
        if (StringUtils.isNotBlank(publicKeyValue)) {
            return publicKeyValue;
        }
        Resource resource = new ClassPathResource(publicKey);
        try {
            InputStreamReader inputStreamReader = new InputStreamReader(resource.getInputStream());
            BufferedReader br = new BufferedReader(inputStreamReader);
            publicKeyValue = br.lines().collect(Collectors.joining("\n"));
            return publicKeyValue;
        } catch (IOException ioe) {
            return null;
        }
    }
}
