package com.rr.pm.designpattern.factory;

import com.rr.pm.util.LogUtils;

/**
 * [类功能说明]
 *
 * @author lary.huang
 * @version v 1.4.8 2017/6/7 XLXZ Exp $
 * @email huangyang@xianglin.cn
 */
public class CarProductA extends Car {
    @Override
    public void engine() {
        System.out.println("this is carproductA engine!");
    }

    @Override
    public void tire() {
        System.out.println("this is carproductA tire");
    }
}
