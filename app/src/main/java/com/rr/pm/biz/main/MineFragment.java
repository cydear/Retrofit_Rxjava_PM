package com.rr.pm.biz.main;

import android.os.Bundle;
import android.view.View;

import com.rr.pm.R;
import com.rr.pm.base.BaseFragment;

/**
 * [类功能说明]
 *
 * @author lary.huang
 * @version v 1.4.8 2017/8/10 XLXZ Exp $
 * @email huangyang@xianglin.cn
 */
public class MineFragment extends BaseFragment {
    public static MineFragment newInstance() {
        return new MineFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_mine;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {

    }
}
