package com.rr.pm.exception;

import android.content.Context;
import android.os.Looper;

import com.rr.pm.PMApplication;
import com.rr.pm.R;
import com.rr.pm.util.LogUtils;
import com.rr.pm.util.ThreadPool;
import com.rr.pm.util.ToastUtils;

/**
 * 公共处理未捕获异常
 *
 * @author huangyang
 * @version v 1.4.8 2017/4/20 XLXZ Exp $
 * @email huangyang@xianglin.cn
 */
public class CustomCrashHandler implements Thread.UncaughtExceptionHandler {
    private Context mContext;
    //默认处理异常的类
    private Thread.UncaughtExceptionHandler mDefaultHandler;

    private final static class SingleHolder {
        private static CustomCrashHandler instance = new CustomCrashHandler();
    }

    public static CustomCrashHandler getInstance() {
        return SingleHolder.instance;
    }

    public void init(Context context) {
        mContext = context;
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        //如果未做特殊异常处理，则交由系统处理
        if (!handleException(e) && mDefaultHandler != null) {
            mDefaultHandler.uncaughtException(t, e);
        } else {
            LogUtils.e(e.getMessage());
//            try {
//                LogUtils.e(e.getMessage());
//                Thread.sleep(2000);
//            } catch (InterruptedException e1) {
//                LogUtils.e(e1.getMessage());
//            }
//            android.os.Process.killProcess(android.os.Process.myPid());
//            System.exit(0);
//            //exit looper
//            Looper.myLooper().quit();
        }
    }

    /**
     * 自定义异常处理，发送错误报告或者保存日志均在此处处理
     *
     * @param e
     * @return
     */
    private boolean handleException(Throwable e) {
        if (e == null) {
            return false;
        }
        showTips(R.string.app_exit_exception);
        return true;
    }

    /**
     * show tips
     *
     * @param resId
     */
    private void showTips(int resId) {
        ThreadPool.getCacheThreadPool().execute(new Runnable() {
            @Override
            public void run() {
                Looper.prepare();
                ToastUtils.show(PMApplication.getInstance(), resId);
                Looper.loop();
            }
        });
    }
}
