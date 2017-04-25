package com.rr.pm;

import android.support.multidex.MultiDexApplication;

import com.facebook.stetho.Stetho;
import com.rr.pm.exception.CustomCrashHandler;
import com.rr.pm.http.AppInfo;
import com.rr.pm.util.LogUtils;

/**
 * 全局application
 *
 * @author huangyang
 * @version v 1.4.8 2017/4/18 XLXZ Exp $
 * @email huangyang@xianglin.cn
 */
public class PMApplication extends MultiDexApplication {
    private static PMApplication instance;

    //日志打印开关
    public static boolean printer = true;
    //是否使用自动堆栈管理
    public static boolean isUseActivityManager = true;

    public static PMApplication getInstance() {
        return instance;
    }

    @Override
    public synchronized void onCreate() {
        super.onCreate();
        instance = this;
        initAssert();
    }

    private void initAssert() {
        //初始化全局异常信息处理
        CustomCrashHandler.getInstance().init(this);
        //初始化logger
        LogUtils.init("pmlog");
         /* 初始化app基本信息 */
        AppInfo.createInstance(instance);
        //初始化stetho
        Stetho.initialize(Stetho.newInitializerBuilder(this)
                .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this))
                .build());
    }
}
