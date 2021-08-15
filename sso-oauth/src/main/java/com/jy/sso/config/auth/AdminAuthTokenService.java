package com.jy.sso.config.auth;

import cn.hutool.json.JSONUtil;
import com.jy.common.exception.CastException;
import com.jy.common.web.ResultBeanCode;
import com.jy.sso.properties.EncryptProperties;
import com.jy.sso.properties.SsoOauthProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.jwt.crypto.sign.RsaSigner;
import org.springframework.security.jwt.crypto.sign.RsaVerifier;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.security.KeyPair;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.util.HashMap;
import java.util.Map;

/**
 * 管理员证书管理
 * 
 * @author jinchunzhao
 * @version 1.0
 * @date 2021-08-14 20:20
 */
@Component
public class AdminAuthTokenService {

    @Autowired
    private EncryptProperties encryptProperties;

    @Autowired
    private SsoOauthProperties ssoOauthProperties;

    /**
     * 获取管理员认证token
     *
     * @param roles
     *            角色列表
     * @return 管理员认证token
     */
    public String getAdminAuthToken(String[] roles) {

        KeyPair keyPair = getKeyPair();

        // 获取私钥 -> RSA算法
        RSAPrivateKey rsaPrivateKey = (RSAPrivateKey) keyPair.getPrivate();

        // 创建令牌，需要私钥加盐[RSA算法]
        Map<String, Object> payload = new HashMap<>();
        payload.put("username", "admin");
        payload.put("address", "China");
        payload.put("authorities", roles);
        Jwt jwt = JwtHelper.encode(JSONUtil.toJsonStr(payload), new RsaSigner(rsaPrivateKey));
        String encoded = jwt.getEncoded();

        return encoded;
    }

    /**
     * 解析token
     *
     * @param token
     *            token
     * @return 数据
     */
    public String parseToken(String token) {

        KeyPair keyPair = getKeyPair();
        PublicKey aPublic = keyPair.getPublic();
        byte[] encoded = aPublic.getEncoded();
        String publicKeyToken = null;

        try {
            publicKeyToken = new String(encoded, "utf-8");
        } catch (UnsupportedEncodingException e) {
            CastException.cast(ResultBeanCode.ERROR);
        }
        Jwt jwt = JwtHelper.decodeAndVerify(token, new RsaVerifier(publicKeyToken));
        String claims = jwt.getClaims();
        return claims;
    }

    /**
     * 获取证书信息
     *
     * @return 证书信息
     */
    private KeyPair getKeyPair() {
        String encryptLocation = encryptProperties.getLocation();
        String clientSecret = ssoOauthProperties.getClientSecret();
        String clientId = ssoOauthProperties.getClientId();
        // 加载证书 读取类路径中的文件
        ClassPathResource resource = new ClassPathResource(encryptLocation);
        // 读取证书数据
        KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(resource, clientSecret.toCharArray());
        // 获取证书中的一对秘钥
        KeyPair keyPair = keyStoreKeyFactory.getKeyPair(clientId, clientSecret.toCharArray());
        return keyPair;
    }

}
