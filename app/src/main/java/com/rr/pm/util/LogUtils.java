package com.rr.pm.util;

import com.orhanobut.logger.Logger;
import com.rr.pm.PMApplication;

/**
 * 日志输出工具
 *
 * @author huangyang
 * @version v 1.4.8 2017/4/18 XLXZ Exp $
 * @email huangyang@xianglin.cn
 */
public class LogUtils {
    /**
     * when application was created,Logger init once
     *
     * @param tag
     */
    public static void init(String tag) {
        Logger.init(tag);
    }

    public static void log(int priority, String tag, String message, Throwable throwable) {
        if (PMApplication.printer) {
            Logger.log(priority, tag, message, throwable);
        }
    }

    public static void d(String message, Object... args) {
        if (PMApplication.printer) {
            Logger.d(message, args);
        }
    }

    public static void d(Object object) {
        if (PMApplication.printer) {
            Logger.d(object);
        }
    }

    public static void e(String message, Object... args) {
        if (PMApplication.printer) {
            Logger.e(null, message, args);
        }
    }

    public static void e(Throwable throwable, String message, Object... args) {
        if (PMApplication.printer) {
            Logger.e(throwable, message, args);
        }
    }

    public static void i(String message, Object... args) {
        if (PMApplication.printer) {
            Logger.i(message, args);
        }
    }

    public static void v(String message, Object... args) {
        if (PMApplication.printer) {
            Logger.v(message, args);
        }
    }

    public static void w(String message, Object... args) {
        if (PMApplication.printer) {
            Logger.w(message, args);
        }
    }

    public static void wtf(String message, Object... args) {
        if (PMApplication.printer) {
            Logger.wtf(message, args);
        }
    }

    /**
     * Formats the json content and print it
     *
     * @param json the json content
     */
    public static void json(String json) {
        if (PMApplication.printer) {
            Logger.json(json);
        }
    }

    /**
     * Formats the json content and print it
     *
     * @param xml the xml content
     */
    public static void xml(String xml) {
        if (PMApplication.printer) {
            Logger.xml(xml);
        }
    }
}
