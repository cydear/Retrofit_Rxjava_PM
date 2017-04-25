package com.rr.pm.http;

import com.rr.pm.util.json.GsonUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * request参数封装
 *
 * @author huangyang
 * @version v 1.4.8 2017/4/18 XLXZ Exp $
 * @email huangyang@xianglin.cn
 */
public class HttpRequestWrapper {
    //请求参数 指定请求的就可接口全名称
    public static final String OPERATION_TYPE = "operationType";
    //请求参数 数据部分
    public static final String REQUEST_DATA = "requestData";
    //请求接口的签名 加签
    public static final String SIGNATURE_D = "d";
    //请求接口的id
    public static final String ID = "id";

    //获取请求接口的id 随机生成一个整型的数字
    public static final AtomicInteger rpcSequence = new AtomicInteger();

    public static Map<String, String> getParamWrapper(String method, Object requestBean) {
        //包装请求参数
        List<Object> listParam = new ArrayList<>();
        listParam.add(requestBean);
        String requestData = GsonUtils.GsonString(listParam);
        Map<String, String> map = new HashMap<>();
        map.put(OPERATION_TYPE, method);
        map.put(REQUEST_DATA, requestData);
        map.put(SIGNATURE_D, ShaUtil.sha512(requestData));
        map.put(ID, String.valueOf(rpcSequence.incrementAndGet()));
        return map;
    }
}
