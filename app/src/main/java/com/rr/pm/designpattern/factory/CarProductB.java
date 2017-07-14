package com.rr.pm.designpattern.factory;

/**
 * @author lary.huang
 * @version v 1.4.8 2017/6/7 XLXZ Exp $
 * @email huangyang@xianglin.cn
 */
public class CarProductB extends Car {
    @Override
    public void engine() {
        System.out.println("this is carproductB engine!");
    }

    @Override
    public void tire() {
        System.out.println("this id carproductB tire!");
    }
}
