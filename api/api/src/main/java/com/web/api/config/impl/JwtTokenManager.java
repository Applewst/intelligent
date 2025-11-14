package com.web.api.config.impl;

import com.alibaba.fastjson2.JSONObject;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.web.api.config.JwtProperties;
import com.web.api.pojo.JwtData;
import com.web.api.util.EncodesUtil;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.View;
import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * JWT令牌管理类
 * @author Askr-Yggdrasill
 */
@Slf4j
@Service
@EnableConfigurationProperties({JwtProperties.class})
public class JwtTokenManager {

    @Autowired
    JwtProperties jwtProperties;

    @Autowired
    private View error;

    /**
     * 解析密钥
     * @return
     */
    private byte[] getSecretKeyBytes() {
        String base64Secret = jwtProperties.getBase64EncodeSecretKey();
        return Base64.getDecoder().decode(base64Secret); // 解码一次，得到原始密钥字节
    }


    /**
     * 签发令牌
     * @param iss 签发者
     * @param ttlMillis 过期时间
     * @param sessionId 唯一标识
     * @param claims jwt存储的一些非隐私信息
     * @return String jwt令牌
     */
    public String issuedToken(String iss, Long ttlMillis, String sessionId, Map<String, Object> claims) {

        //初始化claims
        if (claims == null || claims.isEmpty()) {
            claims = new HashMap<>();
        }

        //获取当前时间
        long nowMillis = System.currentTimeMillis();

        //获取加密签名
        String base64EncodeSecretKey = EncodesUtil.encodeBase64(jwtProperties.getBase64EncodeSecretKey().getBytes());

        //获取原始密钥字节
        byte[] secretBytes = getSecretKeyBytes();

        //创建SecretKey
        SecretKey key = Keys.hmacShaKeyFor(secretBytes); // JJWT 需要 SecretKey

        //生成令牌
        JwtBuilder token = Jwts.builder()
                .id(sessionId) //唯一标识
                .claims(claims) //存储信息
                .issuer(iss) //签发者
                .issuedAt(new Date(nowMillis)) //签发时间
                .signWith(key,Jwts.SIG.HS256); //加密方式

        //设置过期时间
        if(ttlMillis > 0){
            //有效期 = 现在时间 + 过期时间
            long expMillis = nowMillis + ttlMillis;
            Date exp = new Date(expMillis);
            token.expiration(exp); //过期时间
        }

        //转成字符串
        return token.compact();
    }

    /**
     * 解析令牌
     * @param token jwt令牌
     * @return Claims 载荷信息
     */
    public Claims decodeToken(String token){
        //获取加密签名
        String base64EncodeSecretKey = EncodesUtil.encodeBase64(jwtProperties.getBase64EncodeSecretKey().getBytes());

        //获取原始密钥字节
        byte[] secretBytes = getSecretKeyBytes();

        //创建SecretKey
        SecretKey key = Keys.hmacShaKeyFor(secretBytes); // JJWT 需要 SecretKey

        //带着密码去解析
        return Jwts.parser()
                .verifyWith(key)    //设置签名密码
                .build()
                .parseSignedClaims(token)  //解析token
                .getPayload();      //获取载荷
    }

    /**
     * 验证令牌
     * @param token jwt令牌
     * @return boolean 是否成功
     */
    public boolean isVerifyToken(String token){
        //构建校验对象

        //获取原始密钥字节
        byte[] secretBytes = getSecretKeyBytes();

        //Algorithm.HMAC256()方法内部会对密钥进行Base64编码
        Algorithm algorithm = Algorithm.HMAC256(secretBytes);
        JWTVerifier jwtVerifier = JWT.require(algorithm).build();

        //校验(过不了报异常)
        try {
            jwtVerifier.verify(token);
            return true;
        } catch (JWTVerificationException e) {
            log.warn(e.getMessage());
            return false;
        }
    }

    /**
     * 获取当前用户角色
     * @param jwtToken jwt令牌
     * @return 角色
     */
    public String getCurrentUserRole(String jwtToken) {
        Claims claims = decodeToken(jwtToken);
        String userJson = (String) claims.get("user");
        JwtData jwtData = JSONObject.parseObject(userJson, JwtData.class);
        return jwtData.getIdentity();
    }
}
















