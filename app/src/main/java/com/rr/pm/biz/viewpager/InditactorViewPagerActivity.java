package com.rr.pm.biz.viewpager;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.rr.pm.R;
import com.rr.pm.base.BaseActivity;
import com.rr.pm.widget.CircleIndicatorView;

import java.util.concurrent.Executors;

/**
 * [类功能说明]
 *
 * @author lary.huang
 * @version v 1.4.8 2017/8/10 XLXZ Exp $
 * @email huangyang@xianglin.cn
 */
public class InditactorViewPagerActivity extends BaseActivity {
    private static final int RES[] = {R.mipmap.image1, R.mipmap.image2, R.mipmap.image3, R.mipmap.image4};
    private ViewPager mViewPager;
    private ViewPagerAdapter mAdapter;
    private CircleIndicatorView mCircleIndicatorView;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_inditactor_viewpager;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        mCircleIndicatorView = (CircleIndicatorView) findViewById(R.id.indicator_view);
        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        mAdapter = new ViewPagerAdapter();

        mViewPager.setAdapter(mAdapter);

        mCircleIndicatorView.setCount(4);
        mCircleIndicatorView.setViewPager(mViewPager);
        mCircleIndicatorView.startAutoScroll();
    }

    class ViewPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return RES.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view = LayoutInflater.from(InditactorViewPagerActivity.this).inflate(R.layout.view_pager_item, null);
            ImageView imageView = (ImageView) view.findViewById(R.id.item_image);
            imageView.setImageResource(RES[position]);
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }
}
