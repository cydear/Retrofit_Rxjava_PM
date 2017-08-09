package com.rr.pm.biz;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.widget.TextView;

import com.rr.pm.R;
import com.rr.pm.base.ToolbarActivity;
import com.rr.pm.biz.main.HomeFragment;
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

        showAppbarLayout(false);
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
        return HomeFragment.newInstance();
    }
}
