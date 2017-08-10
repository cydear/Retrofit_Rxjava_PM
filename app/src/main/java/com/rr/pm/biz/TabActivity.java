package com.rr.pm.biz;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.TextView;

import com.rr.pm.R;
import com.rr.pm.base.ToolbarActivity;
import com.rr.pm.biz.main.CircleFragment;
import com.rr.pm.biz.main.DiscoveryFragment;
import com.rr.pm.biz.main.HomeFragment;
import com.rr.pm.biz.main.MineFragment;
import com.rr.pm.util.LogUtils;
import com.rr.pm.util.ToastUtils;

/**
 * @author huangyang
 * @version v 1.4.8 2017/4/18 XLXZ Exp $
 * @email huangyang@xianglin.cn
 */
public class TabActivity extends ToolbarActivity {
    private final static int[] mTabRes = {R.mipmap.home, R.mipmap.discovery, R.mipmap.circle, R.mipmap.mine};
    private final static int[] mTabResSelected = {R.mipmap.home_selected, R.mipmap.discovery_selected, R.mipmap.circle_selected, R.mipmap.mine_selected};
    private final static int[] mTabItemIds = {R.id.home, R.id.discovery, R.id.circle, R.id.mine};
    private final static String[] mTitles = {"首页", "发现", "广场", "我的"};
    private final static String HOME_TAB = "home_tab";
    private final static String DISCOVERY_TAB = "discovery_tab";
    private final static String CIRCLE_TAB = "circle_tab";
    private final static String MINE_TAB = "mine_tab";

    private TabLayout mTabLayout;
    private HomeFragment mHomeFragment;
    private DiscoveryFragment mDiscoveryFragment;
    private CircleFragment mCicleFragment;
    private MineFragment mMineFragment;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_tab;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        setToolBarTitle("首页");
        showBack(false);
        showRightTV(false);

        mTabLayout = (TabLayout) findViewById(R.id.tab_bottom);
        mTabLayout.setTabMode(TabLayout.MODE_FIXED);
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                for (int i = 0; i < mTabLayout.getTabCount(); i++) {
                    TextView tabItem = (TextView) mTabLayout.getTabAt(i).getCustomView();
                    if (i == tab.getPosition()) {
                        tabItem.setTextColor(getResources().getColor(R.color.textColorPrimary));
                        tabItem.setCompoundDrawables(null, handleDrawable(mTabResSelected[i]), null, null);
                    } else {
                        tabItem.setTextColor(getResources().getColor(R.color.gray2));
                        tabItem.setCompoundDrawables(null, handleDrawable(mTabRes[i]), null, null);
                    }
                }

                changeTab(tab);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        for (int i = 0; i < mTabRes.length; i++) {
            TextView tabItem = (TextView) getLayoutInflater().inflate(R.layout.tab_item, null);
            tabItem.setId(mTabItemIds[i]);
            tabItem.setText(mTitles[i]);
            tabItem.setCompoundDrawables(null, handleDrawable(mTabRes[i]), null, null);
            mTabLayout.addTab(mTabLayout.newTab().setCustomView(tabItem));
        }

        mHomeFragment = HomeFragment.newInstance();
        mDiscoveryFragment = DiscoveryFragment.newInstance();
        mCicleFragment = CircleFragment.newInstance();
        mMineFragment = MineFragment.newInstance();

        showAppbarLayout(true);

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment homeFragment = getSupportFragmentManager().findFragmentByTag(HOME_TAB);
        Fragment discoveryFragment = getSupportFragmentManager().findFragmentByTag(DISCOVERY_TAB);
        Fragment circleFragment = getSupportFragmentManager().findFragmentByTag(CIRCLE_TAB);
        Fragment mineFragment = getSupportFragmentManager().findFragmentByTag(MINE_TAB);

        if (homeFragment == null) {
            ft.add(getFragmentContentId(), mHomeFragment, HOME_TAB);
        }
        if (discoveryFragment == null) {
            ft.add(getFragmentContentId(), mDiscoveryFragment, DISCOVERY_TAB).hide(mDiscoveryFragment);
        }
        if (circleFragment == null) {
            ft.add(getFragmentContentId(), mCicleFragment, CIRCLE_TAB).hide(mCicleFragment);
        }
        if (mineFragment == null) {
            ft.add(getFragmentContentId(), mMineFragment, MINE_TAB).hide(mMineFragment);
        }

        ft.commitAllowingStateLoss();
    }

    /**
     * 切换Fragment
     *
     * @param tab
     */
    private void changeTab(TabLayout.Tab tab) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment homeFragment = getSupportFragmentManager().findFragmentByTag(HOME_TAB);
        Fragment discoveryFragment = getSupportFragmentManager().findFragmentByTag(DISCOVERY_TAB);
        Fragment circleFragment = getSupportFragmentManager().findFragmentByTag(CIRCLE_TAB);
        Fragment mineFragment = getSupportFragmentManager().findFragmentByTag(MINE_TAB);
        if (ft == null) {
            return;
        }
        if (homeFragment == null || discoveryFragment == null || circleFragment == null || mineFragment == null) {
            return;
        }
        int tabId = tab.getCustomView().getId();
        switch (tabId) {
            case R.id.home:
                ft.show(homeFragment);
                ft.hide(discoveryFragment);
                ft.hide(circleFragment);
                ft.hide(mineFragment);
                ft.commitAllowingStateLoss();
                setToolBarTitle("首页");
                break;
            case R.id.discovery:
                ft.hide(homeFragment);
                ft.show(discoveryFragment);
                ft.hide(circleFragment);
                ft.hide(mineFragment);
                ft.commitAllowingStateLoss();
                setToolBarTitle("发现");
                break;
            case R.id.circle:
                ft.hide(homeFragment);
                ft.hide(discoveryFragment);
                ft.show(circleFragment);
                ft.hide(mineFragment);
                ft.commitAllowingStateLoss();
                setToolBarTitle("广场");
                break;
            case R.id.mine:
                ft.hide(homeFragment);
                ft.hide(discoveryFragment);
                ft.hide(circleFragment);
                ft.show(mineFragment);
                ft.commitAllowingStateLoss();
                setToolBarTitle("我的");
                break;
        }
    }

    private Drawable handleDrawable(int resId) {
        Drawable drawable = getResources().getDrawable(resId);
        if (drawable == null) {
            return null;
        }
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        return drawable;
    }

    @Override
    protected int getFragmentContentId() {
        return R.id.content_frame;
    }

    @Override
    protected Fragment getFragment() {
        return null;
    }
}
