package com.rr.pm.util;

import com.rr.pm.PMApplication;
import com.rr.pm.exception.RpcException;

import java.lang.reflect.InvocationTargetException;

/**
 * 网路请求异常处理
 *
 * @author huangyang
 * @version v 1.4.8 2017/4/18 XLXZ Exp $
 * @email huangyang@xianglin.cn
 */
public class ExceptionUtils {
    /**
     * 异常处理
     *
     * @param e
     */
    public static void exceptionHandler(Throwable e) {
        Exception exception = (Exception) e;
        if (exception instanceof InvocationTargetException) {
            exception = (Exception) ((InvocationTargetException) e).getTargetException();
        }

        if (exception instanceof RpcException) {
            RpcException rpcException = (RpcException) exception;
            switch (rpcException.getCode()) {
                case RpcException.ErrorCode.SERVER_SESSIONSTATUS:
                case RpcException.ErrorCode.SERVER_UNKNOWERROR:
                case RpcException.ErrorCode.CLIENT_NETWORK_UNAVAILABLE_ERROR:
                case RpcException.ErrorCode.CLIENT_NETWORK_SOCKET_ERROR:
                case RpcException.ErrorCode.CLIENT_NETWORK_ERROR:
                    ToastUtils.show(PMApplication.getInstance(), rpcException.getCode() + "==>" + rpcException.getMessage());
                    break;
                default:
                    ToastUtils.show(PMApplication.getInstance(), rpcException.getCode() + "==>" + rpcException.getMessage());
                    break;
            }
        } else {
            LogUtils.e(e.getMessage());
        }
    }
}
