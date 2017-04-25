package com.rr.pm.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 日期工具类
 *
 * @author huangyang
 * @version v 1.4.8 2017/4/21 XLXZ Exp $
 * @email huangyang@xianglin.cn
 */
public class DateUtils {
    //用于存储不同日期模板的DateFormat
    private static Map<String, ThreadLocal<SimpleDateFormat>> dateFormatMap = new HashMap<>();

    /**
     * 根据Pattern创建DateFormat,每个线程只会创建一次
     *
     * @param pattern
     * @return
     */
    private static SimpleDateFormat getSimpleDateFormat(String pattern) {
        //首先从内存中查找是否已经存在
        ThreadLocal<SimpleDateFormat> sdf = dateFormatMap.get(pattern);
        if (sdf == null) {
            synchronized (DateUtils.class) {
                sdf = dateFormatMap.get(pattern);
                if (sdf == null) {
                    //如果内存中不存在,则创建并存储
                    sdf = new ThreadLocal<SimpleDateFormat>() {
                        @Override
                        protected SimpleDateFormat initialValue() {
                            return new SimpleDateFormat(pattern);
                        }
                    };
                    dateFormatMap.put(pattern, sdf);
                }
            }
        }
        return sdf.get();
    }

    /**
     * 根据指定pattern对Date做转换
     *
     * @param date
     * @param pattern
     * @return
     */
    public static String format(Date date, String pattern) {
        return getSimpleDateFormat(pattern).format(date);
    }

    /**
     * 根据指定pattern将string转换成date
     *
     * @param sourceDate
     * @param pattern
     * @return
     */
    public static Date parse(String sourceDate, String pattern) {
        Date date = null;
        try {
            date = getSimpleDateFormat(pattern).parse(sourceDate);
        } catch (ParseException e) {
        }
        return date;
    }
}
