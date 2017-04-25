package com.rr.pm.designpattern.strategy;

/**
 * 策略模式
 * 策略模式定义了一系列算法，并将每一个算法封装起来，而且使他们还可以相互替换。策略模式让算法独立于使用它的客户而独立变换
 * 软件设计原则：对扩展开放，对修改封闭
 * 理解：定义一些列接口由不同子类根据需求去实现，然后通过多态特性实现功能独立。
 *
 * @author huangyang
 * @version v 1.4.8 2017/4/24 XLXZ Exp $
 * @email huangyang@xianglin.cn
 */
public interface Travel {
    void travel();
}
