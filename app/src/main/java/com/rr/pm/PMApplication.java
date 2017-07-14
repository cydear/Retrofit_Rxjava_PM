package com.rr.pm;

import android.annotation.TargetApi;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

import com.facebook.stetho.Stetho;
import com.rr.pm.exception.CustomCrashHandler;
import com.rr.pm.http.AppInfo;
import com.rr.pm.util.LogUtils;
import com.rr.pm.util.TinkerLogImpl;
import com.rr.pm.util.TinkerManager;
import com.tencent.tinker.anno.DefaultLifeCycle;
import com.tencent.tinker.lib.tinker.Tinker;
import com.tencent.tinker.lib.tinker.TinkerInstaller;
import com.tencent.tinker.loader.app.DefaultApplicationLike;
import com.tencent.tinker.loader.shareutil.ShareConstants;

/**
 * 全局application
 *
 * @author huangyang
 * @version v 1.4.8 2017/4/18 XLXZ Exp $
 * @email huangyang@xianglin.cn
 */
@SuppressWarnings("unused")
@DefaultLifeCycle(application = "com.rr.pm.TinkerPMApplication",
        flags = ShareConstants.TINKER_ENABLE_ALL,
        loadVerifyFlag = false)
public class PMApplication extends DefaultApplicationLike {
    private static Application instance;

    //日志打印开关
    public static boolean printer = true;
    //是否使用自动堆栈管理
    public static boolean isUseActivityManager = true;

    public static Application getInstance() {
        return instance;
    }

    @Override
    public synchronized void onCreate() {
        super.onCreate();
        instance = getApplication();
        initAssert();
    }

    private void initAssert() {
        //初始化全局异常信息处理
        CustomCrashHandler.getInstance().init(instance);
        //初始化logger
        LogUtils.init("pmlog");
         /* 初始化app基本信息 */
        AppInfo.createInstance(instance);
        //初始化stetho
        Stetho.initialize(Stetho.newInitializerBuilder(instance)
                .enableDumpapp(Stetho.defaultDumperPluginsProvider(instance))
                .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(instance))
                .build());
    }

    public PMApplication(Application application, int tinkerFlags, boolean tinkerLoadVerifyFlag,
                         long applicationStartElapsedTime, long applicationStartMillisTime, Intent tinkerResultIntent) {
        super(application, tinkerFlags, tinkerLoadVerifyFlag, applicationStartElapsedTime, applicationStartMillisTime, tinkerResultIntent);
    }

    /**
     * install multiDex before install tinker
     * so we don't need to put the tinker lib classes in the main dex
     *
     * @param base
     */
    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    @Override
    public void onBaseContextAttached(Context base) {
        super.onBaseContextAttached(base);
        //you must install multiDex whatever tinker is installed!
        MultiDex.install(base);

        //设置Tinker管理类  保存当前对象
        TinkerManager.setTinkerApplicationLike(this);
        //崩溃保护
        TinkerManager.initFastCrashProtect();
        //should set before tinker is installed
        TinkerManager.setUpgradeRetryEnable(true);

        //optional set logIml, or you can use default debug log
        TinkerInstaller.setLogIml(new TinkerLogImpl());

        TinkerManager.installTinker(this);
        Tinker tinker = Tinker.with(getApplication());
    }

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    public void registerActivityLifecycleCallbacks(Application.ActivityLifecycleCallbacks callback) {
        getApplication().registerActivityLifecycleCallbacks(callback);
    }
}
