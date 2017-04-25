package com.rr.pm.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.rr.pm.util.ActivityManagerTool;
import com.rr.pm.util.ActivityUtils;

import org.greenrobot.eventbus.EventBus;

/**
 * activity基类
 *
 * @author huangyang
 * @version v 1.4.8 2017/4/18 XLXZ Exp $
 * @email huangyang@xianglin.cn
 */
public abstract class BaseActivity extends AppCompatActivity {
    /*页面传递参数*/
    public final static String BUNDLE = "bundle";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityManagerTool.getActivityManager().add(this);
        setContentView(getLayoutId());
        /*处理传递过来的intent*/
        handleIntent(getIntent());
        /*将Fragment添加至activity*/
        addFragmentToActivity();
        /*初始化view*/
        initView(savedInstanceState);
        /*设置点击事件监听*/
        initClickListener();
        /*初始化数据*/
        initData();
    }

    /*设置布局id*/
    protected abstract int getLayoutId();

    /*初始化view*/
    protected abstract void initView(Bundle savedInstanceState);

    /*设置点击事件监听*/
    protected void initClickListener() {

    }

    /*初始化数据*/
    protected void initData() {

    }

    /**
     * 处理传递过来的intent
     *
     * @param intent
     */
    protected void handleIntent(Intent intent) {

    }

    /*设置要添加至frameLayout容器的FragmentId*/
    protected int getFragmentContentId() {
        return 0;
    }

    /*设置Fragment*/
    protected Fragment getFragment() {
        return null;
    }

    /**
     * 将Fragment添加至activity
     */
    protected void addFragmentToActivity() {
        Fragment fragment = getSupportFragmentManager().findFragmentById(getFragmentContentId());
        if (fragment == null) {
            fragment = getFragment();
            if (fragment != null) {
                ActivityUtils.addFragmentToActivity(
                        getSupportFragmentManager(),
                        fragment,
                        getFragmentContentId()
                );
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityManagerTool.getActivityManager().removeActivity(this);
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }
}
