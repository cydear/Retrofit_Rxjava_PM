package com.rr.pm.designpattern.observer;

/**
 * 观察者  用于对被观察者发送的消息进行响应
 *
 * @author huangyang
 * @version v 1.4.8 2017/4/24 XLXZ Exp $
 * @email huangyang@xianglin.cn
 */
public interface Observer<T> {
    void onUpdate(Observable<T> observable, T data);
}
