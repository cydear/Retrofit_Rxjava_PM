package com.rr.pm.http;

/**
 * @author huangyang
 * @version v 1.4.8 2017/4/18 XLXZ Exp $
 * @email huangyang@xianglin.cn
 */
public interface HttpConstant {
    int TIME_READ = 30;
    int TIMEOUT_CONNECTION = 30;
    int TIME_WRITE = 30;

    /*请求代理*/
    String USER_AGENT_HEADER_NAME = "User-Agent";

    /*设置user-agent的值为android*/
    String USER_AGENT_HEADER_VALUE = "Android";
}
