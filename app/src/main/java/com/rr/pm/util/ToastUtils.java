package com.rr.pm.util;

import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

/**
 * toast工具
 *
 * @author huangyang
 * @version v 1.4.8 2017/4/18 XLXZ Exp $
 * @email huangyang@xianglin.cn
 */
public class ToastUtils {
    private static Toast mToast;

    private ToastUtils() {
        throw new AssertionError();
    }

    public static void show(Context context, int resId) {
        show(context, context.getResources().getText(resId), Toast.LENGTH_SHORT);
    }

    public static void show(Context context, int resId, int duration) {
        show(context, context.getResources().getText(resId), duration);
    }

    public static void show(Context context, CharSequence text) {
        show(context, text, Toast.LENGTH_SHORT);
    }

    public static void show(Context context, CharSequence text, int duration) {
        if (TextUtils.isEmpty(text)) {
            return;
        }
        if (context == null) {
            return;
        }
        if (mToast != null) {
            mToast.setText(text);
        } else {
            //如果这个Context是Activity，而Toast是异步弹出，有可能弹出时Activity已经结束。所以正确使用方法，应该是传入ApplicationContext，避免Toast导致内存泄漏
            mToast = Toast.makeText(context.getApplicationContext(), text, duration);
        }
        mToast.show();
    }

    public static void show(Context context, int resId, Object... args) {
        show(context, String.format(context.getResources().getString(resId), args), Toast.LENGTH_SHORT);
    }

    public static void show(Context context, String format, Object... args) {
        show(context, String.format(format, args), Toast.LENGTH_SHORT);
    }

    public static void show(Context context, int resId, int duration, Object... args) {
        show(context, String.format(context.getResources().getString(resId), args), duration);
    }

    public static void show(Context context, String format, int duration, Object... args) {
        show(context, String.format(format, args), duration);
    }
}
