
package com.rr.pm.config;

/**
 * 程序发布 各开关控制接口类
 *
 * @author songdiyuan
 * @version $Id: ReleaseSwitch.java, v 1.0.0 2015-08-08 下午3:48:32 songdy Exp $
 */
public interface ReleaseSwitch {


    /**
     * ENVController.ENV_DEV 开发
     * ENVController.ENV_TEST 测试
     * ENVController.ENV_PP_PRODUCT;  预生产
     * ENVController.ENV_PRODUCT    生产
     */

    String XL_ENV_VALUE = ENVController.ENV_TEST;

    /**
     * okhttp打印日志开关
     */
    boolean mOkHttpLogSwitch = true;
}
