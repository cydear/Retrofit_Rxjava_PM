package com.rr.pm.designpattern.proxy_dynamic;

/**
 * 真实的实现类
 *
 * @author lary.huang
 * @version v 1.4.8 2017/5/12 XLXZ Exp $
 * @email huangyang@xianglin.cn
 */
public class RealSubject implements Subject {
    @Override
    public int operate(int num1, int num2) {
        return num1 + num2;
    }
}
