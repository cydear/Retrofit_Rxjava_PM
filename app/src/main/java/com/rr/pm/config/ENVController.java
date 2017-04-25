package com.rr.pm.config;

import android.content.Context;

public class ENVController {
    /**
     * 开发
     */
    public static final String ENV_DEV = "ENV_DEV";
    /**
     * 测试
     */
    public static final String ENV_TEST = "ENV_TEST";
    /**
     * 生产
     */
    public static final String ENV_PRODUCT = "ENV_PRODUCT";
    /**
     * 预生产
     */
    public static final String ENV_PP_PRODUCT = "ENV_PP_PRODUCT";

    /**
     * 接口协议地址
     */
    public static final String URL = "http://ggw.dev.xianglin.com/ggw/";

    /**
     * @param context
     * @param env
     */
    public static void initEnv(Context context, String env) {

    }

}
