package com.rr.pm.biz.main;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.rr.pm.R;
import com.rr.pm.base.BaseFragment;
import com.rr.pm.util.ToastUtils;
import com.rr.pm.widget.MedicalImageItemView;

/**
 * [类功能说明]
 *
 * @author lary.huang
 * @version v 1.4.8 2017/8/10 XLXZ Exp $
 * @email huangyang@xianglin.cn
 */
public class CircleFragment extends BaseFragment {
    private Button btnAdd;
    private LinearLayout llContainer;
    private MedicalImageItemView mCurrentTarget;

    public static CircleFragment newInstance() {
        return new CircleFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_circle;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        btnAdd = (Button) view.findViewById(R.id.btn_add);
        llContainer = (LinearLayout) view.findViewById(R.id.ll_container);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addChildView();
            }
        });
    }

    private void addChildView() {
        MedicalImageItemView childView = new MedicalImageItemView(mContext);
        childView.setOnItemImageUploadLisenter(new MedicalImageItemView.OnItemImageUploadLisenter() {
            @Override
            public void onSelectImageListener(ViewGroup target) {
                mCurrentTarget = (MedicalImageItemView) target;
            }

            @Override
            public void onDeleteImageLisetner(ViewGroup target) {
                mCurrentTarget = (MedicalImageItemView) target;
            }
        });
        llContainer.addView(childView);
        llContainer.requestLayout();
    }
}
