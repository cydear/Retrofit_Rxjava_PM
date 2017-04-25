package com.rr.pm.http;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.rr.pm.config.ReleaseSwitch;
import com.rr.pm.http.interceptor.HttpRequestInterceptor;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * @author huangyang
 * @version v 1.4.8 2017/4/18 XLXZ Exp $
 * @email huangyang@xianglin.cn
 */
public class OkHttpFactory {
    private static OkHttpClient okHttpClient;

    public static OkHttpClient defaultOkHttpClient() {
        if (okHttpClient != null) {
            return okHttpClient;
        }

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        if (ReleaseSwitch.mOkHttpLogSwitch) {
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        } else {
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.NONE);
        }

        HttpsUtil.SSLParams sslParams = HttpsUtil.getSslSocketFactory(null, null, null);

        okHttpClient = new OkHttpClient.Builder()
                .readTimeout(HttpConstant.TIME_READ, TimeUnit.SECONDS)
                .writeTimeout(HttpConstant.TIME_WRITE, TimeUnit.SECONDS)
                .connectTimeout(HttpConstant.TIMEOUT_CONNECTION, TimeUnit.SECONDS)
                .sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager)
                .addInterceptor(loggingInterceptor)
                .addInterceptor(new HttpRequestInterceptor())
                .addNetworkInterceptor(new StethoInterceptor())
                .retryOnConnectionFailure(true)
                .build();
        return okHttpClient;
    }
}
