package com.rr.pm.designpattern.factory;

import org.junit.Test;

/**
 * junit
 *
 * @author lary.huang
 * @version v 1.4.8 2017/6/7 XLXZ Exp $
 * @email huangyang@xianglin.cn
 */
public class FactoryJunit {
    @Test
    public void test() {
        AbstractFactory factory = AbstractFactory.createFactory(ConcerentFactoryA.class);
        Car car = factory.getCar();
        car.engine();
        car.tire();

        AbstractFactory factory1 = AbstractFactory.createFactory(ConcerntFactoryB.class);
        Car car1 = factory1.getCar();
        car1.engine();
        car1.tire();
    }
}
