package com.rr.pm.util;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

/**
 * Activity管理工具
 *
 * @author huangyang
 * @version v 1.4.8 2017/4/19 XLXZ Exp $
 * @email huangyang@xianglin.cn
 */
public class ActivityUtils {
    /**
     * The {@code fragment} is added to the container view with id {@code frameId}. The operation is
     * performed by the {@code fragmentManager}.
     *
     * @param fragmentManager
     * @param fragment
     * @param frameId
     * @param tag
     */
    public static void addFragmentToActivity(@NonNull FragmentManager fragmentManager,
                                             @NonNull Fragment fragment,
                                             int frameId,
                                             String tag) {
        if (fragmentManager == null || fragment == null) return;
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.add(frameId, fragment, tag);
        ft.commitAllowingStateLoss();
    }

    /**
     * @param fragmentManager
     * @param fragment
     * @param frameId
     */
    public static void addFragmentToActivity(@NonNull FragmentManager fragmentManager,
                                             @NonNull Fragment fragment,
                                             int frameId) {
        addFragmentToActivity(fragmentManager, fragment, frameId, null);
    }

    /**
     * replace fragment
     *
     * @param fragmentManager
     * @param fragment
     * @param frameId
     */
    public static void replaceFragment(@NonNull FragmentManager fragmentManager,
                                       @NonNull Fragment fragment,
                                       int frameId) {
        if (fragmentManager == null || fragment == null) return;
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.replace(frameId, fragment);
        ft.commit();
    }
}
