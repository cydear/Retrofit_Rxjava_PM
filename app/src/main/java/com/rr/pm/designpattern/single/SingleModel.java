package com.rr.pm.designpattern.single;

/**
 * 单例模式
 * 基本模型
 *
 * @author huangyang
 * @version v 1.4.8 2017/4/24 XLXZ Exp $
 * @email huangyang@xianglin.cn
 */
public class SingleModel {
    private volatile static SingleModel instance;

    /**
     * 不对外提供实例化方法
     */
    private SingleModel() {

    }

    public static SingleModel getInstance() {
        if (instance == null) {
            synchronized (SingleModel.class) {
                if (instance == null) {
                    instance = new SingleModel();
                }
            }
        }
        return instance;
    }
}
