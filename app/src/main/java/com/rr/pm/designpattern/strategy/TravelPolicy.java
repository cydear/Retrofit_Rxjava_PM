package com.rr.pm.designpattern.strategy;

/**
 * 用于封装的策略类
 *
 * @author huangyang
 * @version v 1.4.8 2017/4/24 XLXZ Exp $
 * @email huangyang@xianglin.cn
 */
public class TravelPolicy {
    private Travel travel;

    public static TravelPolicy newInstance() {
        return new TravelPolicy();
    }

    public void setTravel(Travel travel) {
        this.travel = travel;
    }

    public void travel() {
        this.travel.travel();
    }
}
