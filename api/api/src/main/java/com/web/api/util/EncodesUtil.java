package com.web.api.util;

import org.apache.shiro.codec.Base64;
import org.apache.shiro.codec.Hex;

/**
 * 散列算法
 * 测试在client下
 */
public class EncodesUtil {

    /**
     * Hex编码 HEX:String->byte[]
     */
    public static String encoderHex(byte[] input){
        return Hex.encodeToString(input);
    }

    /**
     * Hex解码 HEX:byte[]->String
     */
    public static byte[] decoderHex(String input){
        return Hex.decode(input);
    }

    /**
     * Hex编码 Base64:String->byte[]
     */
    public static String encodeBase64(byte[] input){
        return Base64.encodeToString(input);
    }

    /**
     * Hex解码 Base64:byte[]->String
     */
    public static byte[] decodeBase64(String input) {
        return Base64.decode(input);
    }
}
