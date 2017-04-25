package com.rr.pm.http;

import com.rr.pm.PMApplication;
import com.rr.pm.R;
import com.rr.pm.data.bean.Response;
import com.rr.pm.util.ExceptionUtils;
import com.rr.pm.util.NetworkUtils;
import com.rr.pm.util.ToastUtils;
import com.xianglin.xlnodecore.common.service.facade.enums.FacadeEnums;

import java.lang.reflect.Method;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * 响应报文处理
 *
 * @author huangyang
 * @version v 1.4.8 2017/4/18 XLXZ Exp $
 * @email huangyang@xianglin.cn
 */
public abstract class AbstarctSubscriber<T> implements Observer<Response<T>> {
    @Override
    public void onSubscribe(Disposable disposable) {

    }

    /**
     * 每次请求响应结束后执行  异常后不执行
     */
    @Override
    public void onComplete() {

    }

    /**
     * 异常处理 响应处理异常时回调
     *
     * @param e
     */
    @Override
    public void onError(Throwable e) {
        //异常处理
        onException(e);
    }

    /**
     * 网络请求成功后回调
     *
     * @param resp
     */
    @Override
    public void onNext(Response<T> resp) {
        try {
            T result = resp.getResult();
            Class clazz = result.getClass();
            Method codeMethod = clazz.getMethod("getCode");
            Object codeObj = codeMethod.invoke(result);
            int respCode = -1;
            if (codeObj instanceof Integer) {
                respCode = (int) codeObj;
            } else if (codeObj instanceof String) {
                respCode = Integer.parseInt((String) codeObj);
            }
            if (respCode == FacadeEnums.OK.getCode() || respCode == FacadeEnums.SURE.getCode()) {
                //成功时调用
                onSuccess(resp);
            } else {
                onFail(resp);
            }
        } catch (Exception e) {
            onException(e);
        }
    }

    /**
     * 异常处理
     *
     * @param e
     */
    private void onException(Throwable e) {
        if (!NetworkUtils.isConnected(PMApplication.getInstance())) {
            ToastUtils.show(PMApplication.getInstance(),
                    PMApplication.getInstance().getString(R.string.network_is_not_available));
        } else {
            ExceptionUtils.exceptionHandler(e);
        }
    }

    /**
     * 请求失败时调用
     */
    public abstract void onFail(Response<T> resp);

    /**
     * 成功时调用
     *
     * @param resp
     */
    public abstract void onSuccess(Response<T> resp);
}
