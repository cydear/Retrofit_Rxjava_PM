package com.rr.pm.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.rr.pm.PMApplication;

import java.util.LinkedList;
import java.util.List;

public class ActivityManagerTool {

    /**
     * activity 列表结合
     */
    private List<Activity> activities = new LinkedList<Activity>();
    /**
     * 当前操作对象。
     */
    private static ActivityManagerTool manager;
    /**
     * activity 存在标志
     */
    private boolean isExist = false;
    /**
     * 首页所在的activity所对应的类名，必须在打开首页设置此项
     */
    public static Class<?> indexActivity;
    /**
     * 底部导航类集合
     */
    public static List<Class<?>> bottomActivities = new LinkedList<Class<?>>();

    /**
     * 获得 activity管理对象
     *
     * @return
     */
    public static ActivityManagerTool getActivityManager() {
        if (null == manager) {
            manager = new ActivityManagerTool();
        }
        return manager;
    }

    /**
     * 添加新的activity
     *
     * @param activity
     * @return
     */
    public boolean add(Activity activity) {

        int position = 0;
        // 判断是否自动清除非子activity
        if (PMApplication.isUseActivityManager) {
            // 导航栏activity进栈，删除非导航栏activity
            if (isBottomActivity(activity)) {
                for (int i = 0; i < activities.size() - 1; i++) {

                    if (!isBottomActivity(activities.get(i))) {
                        popActivity(activities.get(i));
                        i--;
                    }
                    if (i > 0) {
                        // 获得重复activity位置
                        if (activities.get(i).getClass()
                                .equals(activity.getClass())) {
                            isExist = true;
                            position = i;
                        }
                    }
                }

            }
        }

        if (!activities.add(activity)) {
            return false;
        }
        // 删除重复activity
        if (isExist) {
            isExist = false;
            activities.remove(position);
        }

        return true;
    }

    /**
     * 关闭除参数activity外的所有activity
     *
     * @param activity
     */
    public void finish(Activity activity) {
        for (Activity iterable : activities) {
            if (activity != iterable) {
                iterable.finish();
            }
        }
    }

    /**
     * 关闭所有的activity
     */
    public void exit() {
        for (Activity activity : activities) {
            if (activity != null) {
                activity.finish();
            }
        }
        System.out.println("退出系统");
        System.exit(0);
    }

    /**
     * 删除指定activity
     *
     * @param activity
     */
    public void popActivity(Activity activity) {

        if (activity != null) {
            activity.finish();
            activities.remove(activity);
        }

    }

    /**
     * 获得当前activity
     *
     * @return
     */
    public Activity currentActivity() {
        Activity activity = activities.get(activities.size() - 1);

        return activity;
    }

    /**
     * activity是否为底部导航
     *
     * @return
     */
    public boolean isBottomActivity(Activity activity) {

        for (int i = 0; i < bottomActivities.size(); i++) {
            if (activity.getClass() != bottomActivities.get(i)) {

            } else {
                return true;
            }
        }

        return false;
    }

    /**
     * 如需返回IndexActivity则返回IndexActivity
     *
     * @param context
     * @param context
     */
    public void backIndex(Context context) {

        if (activities == null || activities.size() <= 0) {
            return;
        }

        if (isBottomActivity(activities.get(activities.size() - 1))) {
            Intent intent = new Intent();
            if (context == null || indexActivity == null) {
                return;
            }
            intent.setClass(context, indexActivity);
            context.startActivity(intent);
        }
    }

    /**
     * 删除已经finish的activity
     *
     * @param activity
     */
    public void removeActivity(Activity activity) {

        if (activity != null) {
            activities.remove(activity);
        }
    }

    /**
     * 初始化，存储底部导航类
     *
     * @param activityClass
     */
    public void setBottomActivities(Class<?> activityClass) {
        if (activityClass != null) {
            bottomActivities.add(activityClass);
        }
    }
}