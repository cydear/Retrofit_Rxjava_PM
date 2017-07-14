package com.rr.pm.designpattern.factory;

/**
 * 抽象工厂
 *
 * @author lary.huang
 * @version v 1.4.8 2017/6/7 XLXZ Exp $
 * @email huangyang@xianglin.cn
 */
public abstract class AbstractFactory {
    public static <T extends AbstractFactory> AbstractFactory createFactory(Class<T> clazz) {
        try {
            AbstractFactory factory = clazz.newInstance();
            return factory;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public abstract Car getCar();
}
