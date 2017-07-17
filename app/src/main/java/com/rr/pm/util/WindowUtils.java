package com.rr.pm.util;

import android.app.Activity;
import android.view.Window;
import android.view.WindowManager;

/**
 * 窗口管理工具类
 *
 * @author lary.huang
 * @version v 1.4.8 2017/6/16 XLXZ Exp $
 * @email huangyang@xianglin.cn
 */
public class WindowUtils {
    /**
     * 设置隐藏标题栏
     *
     * @param activity
     */
    public static void setNoTitle(Activity activity) {
        if (activity == null) return;
        activity.requestWindowFeature(Window.FEATURE_NO_TITLE);
    }

    /**
     * 设置全屏
     *
     * @param activity
     */
    public static void setFullScreen(Activity activity) {
        if (activity == null) return;
        activity.getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    /**
     * 退出全屏
     *
     * @param activity
     */
    public static void cancelFullScreen(Activity activity) {
        if (activity == null) return;
        activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }
}
