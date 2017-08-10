package com.rr.pm.biz.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.rr.pm.R;
import com.rr.pm.base.BaseFragment;
import com.rr.pm.biz.viewpager.InditactorViewPagerActivity;

/**
 * [类功能说明]
 *
 * @author lary.huang
 * @version v 1.4.8 2017/8/10 XLXZ Exp $
 * @email huangyang@xianglin.cn
 */
public class DiscoveryFragment extends BaseFragment implements View.OnClickListener {

    public static DiscoveryFragment newInstance() {
        return new DiscoveryFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_discovery;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        view.findViewById(R.id.btn_viewpager).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int _id = v.getId();
        if (_id == R.id.btn_viewpager) {
            startActivity(new Intent(getActivity(), InditactorViewPagerActivity.class));
        }
    }
}
