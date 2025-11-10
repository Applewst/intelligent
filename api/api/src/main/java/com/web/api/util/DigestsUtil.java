package com.web.api.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import java.util.HashMap;
import java.util.Map;

@Slf4j
/**
 * 散列算法(生成摘要)
 * 测试在client下
 */
public class DigestsUtil {

    //加密算法
    public static final String SHA256 = "SHA-256";
    //迭代次数
    public static final Integer ITERATIONS = 8;

    /**
     * sha-256加密
     * @param input 明文
     * @param salt 盐
     * @return 密文
     */
    private static String sha256(String input, String salt){
        //参数1：加密算法名称 参数2：要加密的内容 参数3：盐 参数4：迭代次数
        String e = new SimpleHash(SHA256,input,salt,ITERATIONS).toString();
        log.error(e);
        return e;
    }

    /**
     * 生成随机盐
     * @return hex编码的salt
     */
    private static String generateSalt() {
        SecureRandomNumberGenerator secureRandom = new SecureRandomNumberGenerator();
        return secureRandom.nextBytes().toHex();

    }

    /**
     * 生成加密后的密码
     * @param plainPassword 明文密码
     * @return map集合，包含盐和密文
     */
    public static Map<String,String> encryptPassword(String plainPassword) {
        String salt = generateSalt();
        String password = sha256(plainPassword, salt);
        Map<String, String> map = new HashMap<>();
        map.put("salt", salt);
        map.put("password", password);
        return map;
    }
}
