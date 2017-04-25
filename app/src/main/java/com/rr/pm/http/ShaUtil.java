package com.rr.pm.http;

import org.apache.shiro.crypto.hash.Sha512Hash;

/**
 * @author huangyang
 * @version v 1.4.8 2017/4/18 XLXZ Exp $
 * @email huangyang@xianglin.cn
 */
public class ShaUtil {
    public static String sha512(String source) {
        return new Sha512Hash(source).toBase64();
    }

    public static String sha512(String source, String salt) {
        return new Sha512Hash(source, salt).toBase64();
    }
}
