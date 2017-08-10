package com.rr.pm.biz;

import android.os.Bundle;
import android.support.design.widget.TabLayout;

import com.rr.pm.R;
import com.rr.pm.base.ToolbarActivity;
import com.rr.pm.biz.main.HomeFragment;

/**
 * @author huangyang
 * @version v 1.4.8 2017/4/18 XLXZ Exp $
 * @email huangyang@xianglin.cn
 */
public class MainActivity extends ToolbarActivity {
    private final static int[] mTabRes = {R.mipmap.home, R.mipmap.discovery, R.mipmap.circle, R.mipmap.mine};
    private final static int[] mTabResSelected = {R.mipmap.home_selected, R.mipmap.discovery_selected, R.mipmap.circle_selected, R.mipmap.mine_selected};

    private TabLayout mTabLayout;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        mTabLayout = (TabLayout) findViewById(R.id.tab);

        setToolBarTitle("首页");
        showBack(false);
        showRightTV(false);
        initTabs();
    }

    private void initTabs() {
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                for (int i = 0; i < mTabLayout.getTabCount(); i++) {
                    if (i == tab.getPosition()) {
                        mTabLayout.getTabAt(i).setIcon(getResources().getDrawable(mTabResSelected[i]));
                    } else {
                        mTabLayout.getTabAt(i).setIcon(getResources().getDrawable(mTabRes[i]));
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


        mTabLayout.addTab(mTabLayout.newTab().setIcon(getResources().getDrawable(R.drawable.tab_home_selector)).setText("首页"));
        mTabLayout.addTab(mTabLayout.newTab().setIcon(getResources().getDrawable(R.drawable.tab_discover_selector)).setText("发现"));
        mTabLayout.addTab(mTabLayout.newTab().setIcon(getResources().getDrawable(R.drawable.tab_circle_selector)).setText("广场"));
        mTabLayout.addTab(mTabLayout.newTab().setIcon(getResources().getDrawable(R.drawable.tab_mine_selector)).setText("我的"));
    }
}
