package com.rr.pm.designpattern.observer;

import java.util.ArrayList;
import java.util.List;

/**
 * 被观察者 用于处理事件并发送消息
 *
 * @author huangyang
 * @version v 1.4.8 2017/4/24 XLXZ Exp $
 * @email huangyang@xianglin.cn
 */
public class Observable<T> {
    List<Observer<T>> observers = new ArrayList<>();

    public void register(Observer<T> observer) {
        if (observer == null) {
            throw new NullPointerException("observer is null");
        }
        synchronized (Observable.class) {
            if (!observers.contains(observer)) {
                observers.add(observer);
            }
        }
    }

    public void unregister(Observer<T> observer) {
        if (observers.contains(observer)) {
            observers.remove(observer);
        }
    }

    public void notifyObservers(T data) {
        for (Observer<T> observer : observers) {
            observer.onUpdate(this, data);
        }
    }
}
