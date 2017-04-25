package com.rr.pm.http;

import android.app.ActivityManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.rr.pm.util.LogUtils;

import java.util.UUID;

/**
 * 应用的管理以及配置信息
 *
 * @author alex
 */
public class AppInfo {
    private static AppInfo mInstance;
    private Context mContext;
    private static final String TAG = "AppInfo";

    /**
     * ActivityManager
     */
    private ActivityManager mActivityManager;

    private String mProductID;

    /**
     * 软件版本
     */
    private String mProductVersion;
    private int mProductVersionCode;

    private String mChannels;

    /**
     * 是否调试版本
     */
    private boolean mDebuggable;
    /**
     * 当前运行的进程ID
     */
    private int mPid;

    private String mAwid;

    private SharedPreferences mSharedPreferences;

    private AppInfo(Context context) {
        mContext = context;

        // 永远是最后一行
        init();
    }

    /**
     * 获取应用的管理以及配置信息实例
     *
     * @return 性能记录器
     */
    public static AppInfo getInstance() {
        if (mInstance == null)
            throw new IllegalStateException(
                    "AppInfo must be created by calling createInstance(Context context)");
        return mInstance;
    }

    /**
     * 创建应用的管理以及配置信息实例
     *
     * @param context 上下文
     * @return
     */
    public static synchronized AppInfo createInstance(Context context) {
        if (mInstance == null) {
            mInstance = new AppInfo(context);
        }
        return mInstance;
    }

    /**
     * 过滤版本号
     *
     * @param versionName
     * @return
     */
    private String clearVersionName(String versionName) {
        if (versionName.contains("ctch1")) {
            versionName = versionName.replace("ctch1", "");
        }
        return versionName;
    }

    /**
     * 初始化
     */
    private void init() {
        try {
            String tpackageName = mContext.getPackageName();
            LogUtils.d("getPackageName " + tpackageName);

            mSharedPreferences = mContext.getSharedPreferences(tpackageName
                    + "_config", Context.MODE_PRIVATE);
            String version = mSharedPreferences.getString("version", null);
            PackageInfo mPackageInfo = mContext.getPackageManager()
                    .getPackageInfo(tpackageName, 0);
            mProductVersion = clearVersionName(mPackageInfo.versionName);
            mProductVersionCode = mPackageInfo.versionCode;
            if (null != version && compareVersion(version, mProductVersion)) {
                mProductVersion = version;
            }

            ApplicationInfo applicationInfo = mContext.getPackageManager()
                    .getApplicationInfo(mContext.getPackageName(),
                            PackageManager.GET_CONFIGURATIONS);
            if ((applicationInfo.flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0) {
                // development mode
                mDebuggable = true;
            }

            mActivityManager = (ActivityManager) mContext
                    .getSystemService(Context.ACTIVITY_SERVICE);
            mPid = android.os.Process.myPid();
            mProductID = "";
            mChannels = "alipay";

            mAwid = UUID.randomUUID().toString();
        } catch (PackageManager.NameNotFoundException e) {
            LogUtils.d("init: " + e == null ? "" : e.getMessage());
        }
    }

    /**
     * 获取manifest文件meta值
     *
     * @param context
     * @param keyName
     * @param defValue
     * @return
     */
    public String getMetaValue(Context context, String keyName, String defValue) {
        Object value = null;
        ApplicationInfo applicationInfo;
        try {
            applicationInfo = context.getPackageManager().getApplicationInfo(context
                    .getPackageName(), PackageManager.GET_META_DATA);

            value = applicationInfo.metaData.get(keyName);

        } catch (Exception e) {
            e.printStackTrace();
        }
        if (value != null) {
            return value.toString();
        } else {
            return defValue;
        }

    }

    /**
     * 比较版本
     *
     * @param version
     * @param mProductVersion
     * @return
     */
    private boolean compareVersion(String version, String mProductVersion) {
        String[] versions = version.split("\\.");
        String[] productVersions = mProductVersion.split("\\.");
        for (int i = 0; i < versions.length; i++) {
            int v1 = Integer.parseInt(versions[i]);
            int v2 = Integer.parseInt(productVersions[i]);
            if (v1 > v2) {
                return true;
            } else if (v1 == v2) {
                continue;
            } else {
                return false;
            }
        }
        return false;
    }

    /**
     * 是否是开发状态
     */
    public boolean isDebuggable() {
        return mDebuggable;
    }

    /**
     * 获取当前运行的进程ID
     *
     * @return 进程ID
     */
    public int getPid() {
        return mPid;
    }

    public void setProductVersion(String version) {
        if (null != version) {
            mSharedPreferences.edit().putString("version", version).commit();
            mProductVersion = version;
        }
    }

    /**
     * 恢复大版本
     */
    public void recoverProductVersion() {
        mSharedPreferences.edit().remove("version").commit();
    }

    public void setChannels(String channels) {
        mSharedPreferences.edit().putString("channels", channels).commit();
        mChannels = channels;
    }

    public void setProductID(String productId) {
        mSharedPreferences.edit().putString("productId", productId).commit();
        mProductID = productId;
    }

    public String getProductID() {
        if (mProductID.equals("")) {
            return "Android-container";
        } else {
            return mProductID;
        }
    }

    public String getProductVersion() {
        return mProductVersion;
    }

    public int getProductVersionCode() {
        return mProductVersionCode;
    }

    public String getChannels() {
        return mChannels;
    }

    public String getAwid() {
        return mAwid;
    }

    /**
     * 获取当前所占内存
     *
     * @return 当前所占内存
     */
    public long getTotalMemory() {
        android.os.Debug.MemoryInfo[] mems = mActivityManager.getProcessMemoryInfo(new int[]{mPid});
        return mems[0].getTotalPrivateDirty();
    }

    /**
     * 获取应用的data/data/....File目录
     *
     * @return File目录
     */
    public String getFilesDirPath() {
        return mContext.getFilesDir().getAbsolutePath();
    }

    /**
     * 获取应用的data/data/....Cache目录
     *
     * @return Cache目录
     */
    public String getCacheDirPath() {
        return mContext.getCacheDir().getAbsolutePath();
    }
}
