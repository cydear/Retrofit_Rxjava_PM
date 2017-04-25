package com.rr.pm.util;

import com.rr.pm.R;
import com.rr.pm.base.BaseActivity;

/**
 * @author huangyang
 * @version v 1.4.8 2017/4/19 XLXZ Exp $
 * @email huangyang@xianglin.cn
 */
public class AnimationUtils {
    /**
     * 进入动画
     *
     * @param activity
     */
    public static void animLeftToRight(BaseActivity activity) {
        try {
            activity.overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
        } catch (Exception e) {
        }
    }

    /**
     * 退出动画
     *
     * @param activity
     */
    public static void animRightToLeft(BaseActivity activity) {
        try {
            activity.overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
        } catch (Exception e) {
        }
    }
}
