package com.rr.pm.designpattern.factory;

/**
 * [类功能说明]
 *
 * @author lary.huang
 * @version v 1.4.8 2017/6/7 XLXZ Exp $
 * @email huangyang@xianglin.cn
 */
public class ConcerntFactoryB extends AbstractFactory {
    @Override
    public Car getCar() {
        return new CarProductB();
    }
}
