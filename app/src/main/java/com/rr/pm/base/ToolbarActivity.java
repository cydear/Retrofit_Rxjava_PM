package com.rr.pm.base;

import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.rr.pm.R;

/**
 * 带toolbar的activity
 *
 * @author huangyang
 * @version v 1.4.8 2017/4/19 XLXZ Exp $
 * @email huangyang@xianglin.cn
 */
public abstract class ToolbarActivity extends BaseActivity {
    private TextView mRightTv;
    private TextView mToolbarTitle;
    private AppBarLayout mAppbarLayout;
    private Toolbar mToolbar;

    @Override
    protected void initView(Bundle savedInstanceState) {
        initToolbar();
    }

    /**
     * 设置toolbar
     */
    private void initToolbar() {
        mAppbarLayout = (AppBarLayout) findViewById(R.id.app_bar_layout);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mRightTv = (TextView) findViewById(R.id.toolbar_right_tv);
        mToolbarTitle = (TextView) findViewById(R.id.toolbar_title);
        //设置toolbar取代actionbar
        setSupportActionBar(mToolbar);
        //toolbar默认会显示应用名称,通过设置setDisplayShowTitleEnabled为false解决问题
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setElevation(0);
        }
        //去除AppBarLayout阴影
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mToolbar.setElevation(0);
        }
        //设置导航点击事件
        mToolbar.setNavigationOnClickListener(v -> onBackPressed());
        //设置最右边textview点击事件
        mRightTv.setOnClickListener(v -> onRightTvClick());
    }

    /**
     * set click event to right textview
     *
     * @description can Override
     */
    private void onRightTvClick() {
        onBackPressed();
    }

    /**
     * set toolbar title
     *
     * @param title
     */
    protected void setToolBarTitle(String title) {
        mToolbarTitle.setText(title);
    }

    protected void setToolBarTitle(int resId) {
        mToolbarTitle.setText(getString(resId));
    }

    /**
     * set right textview
     *
     * @param text
     */
    protected void setRightTv(String text) {
        mRightTv.setText(text);
    }

    protected void setRightTv(int resId) {
        mRightTv.setText(getString(resId));
    }

    /**
     * show or hidden back button
     *
     * @param visible
     */
    protected void showBack(boolean visible) {
        getSupportActionBar().setDisplayHomeAsUpEnabled(visible);
    }

    /**
     * show or hidden right textview
     *
     * @param visible
     */
    protected void showRightTV(boolean visible) {
        mRightTv.setVisibility(visible ? View.VISIBLE : View.GONE);
    }
}
