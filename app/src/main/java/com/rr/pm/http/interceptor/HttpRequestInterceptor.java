package com.rr.pm.http.interceptor;

import com.rr.pm.http.HttpConstant;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 请求报文拦截器
 *
 * @author huangyang
 * @version v 1.4.8 2017/4/18 XLXZ Exp $
 * @email huangyang@xianglin.cn
 */
public class HttpRequestInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        final Request originalRequest = chain.request();
        final Request newRequest = originalRequest.newBuilder()
                .removeHeader(HttpConstant.USER_AGENT_HEADER_NAME)
                .addHeader(HttpConstant.USER_AGENT_HEADER_NAME, HttpConstant.USER_AGENT_HEADER_VALUE)
                .build();
        return chain.proceed(newRequest);
    }
}
