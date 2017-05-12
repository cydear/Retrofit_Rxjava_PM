package com.rr.pm.designpattern.proxy_dynamic;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * 动态代理类
 *
 * @author lary.huang
 * @version v 1.4.8 2017/5/12 XLXZ Exp $
 * @email huangyang@xianglin.cn
 */
public class ProxySubject implements InvocationHandler {
    private Object subject;

    public ProxySubject(Object subject) {
        this.subject = subject;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        return method.invoke(subject, args);
    }
}
