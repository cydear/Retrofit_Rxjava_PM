package com.rr.pm.designpattern.strategy;

import com.rr.pm.util.LogUtils;

/**
 * 步行
 *
 * @author huangyang
 * @version v 1.4.8 2017/4/24 XLXZ Exp $
 * @email huangyang@xianglin.cn
 */
public class WalkTravel implements Travel {
    @Override
    public void travel() {
        LogUtils.d("walking...");
    }
}
