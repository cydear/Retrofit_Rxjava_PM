package com.rr.pm.http;

import com.rr.pm.config.ENVController;
import com.rr.pm.http.convert.StringConvertFactory;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * retrofit
 *
 * @author huangyang
 * @version v 1.4.8 2017/4/18 XLXZ Exp $
 * @email huangyang@xianglin.cn
 */
public class RetrofitClient {
    private Retrofit retrofit;

    private RetrofitClient() {
        retrofit = new Retrofit.Builder()
                //设置OkHttpClient
                .client(OkHttpFactory.defaultOkHttpClient())
                //设置请求的BASE_URL
                .baseUrl(ENVController.URL)
                //设置json解析方式
                .addConverterFactory(GsonConverterFactory.create())
                //设置string解析
                .addConverterFactory(StringConvertFactory.create())
                //Rx解析
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    /**
     * single instance
     */
    public static class SingleHolder {
        private static final RetrofitClient retrofitClient = new RetrofitClient();
    }

    /**
     * instance
     *
     * @return
     */
    public static RetrofitClient getInstance() {
        return SingleHolder.retrofitClient;
    }

    /**
     * the interface provide service
     *
     * @param clazz
     * @param <T>
     * @return
     */
    public <T extends Object> T getService(Class<T> clazz) {
        return retrofit.create(clazz);
    }
}
