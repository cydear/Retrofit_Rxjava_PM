package com.rr.pm.util;

/**
 * string工具类
 *
 * @author huangyang
 * @version v 1.4.8 2017/4/18 XLXZ Exp $
 * @email huangyang@xianglin.cn
 */
public class StringUtils {

    public static boolean isEmpty(String value) {
        return value == null || value.equals("");
    }

    public static boolean isNullOrEmpty(String value) {
        return isEmpty(value);
    }

    /**
     * is null or its length is 0 or it is made by space
     *
     * @param str
     * @return
     */
    public static boolean isBlank(String str) {
        return (str == null || str.trim().length() == 0);
    }
}
