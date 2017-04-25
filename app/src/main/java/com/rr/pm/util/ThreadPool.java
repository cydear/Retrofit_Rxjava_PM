package com.rr.pm.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 固定和不固定线程池
 *
 * @author huangyang
 * @version v 1.4.8 2017/4/19 XLXZ Exp $
 * @email huangyang@xianglin.cn
 */
public class ThreadPool {
    //不固定线程池
    private static ExecutorService cacheThreadPool;
    //单线程池
    private static ExecutorService singleThreadPool;

    /**
     * 不固定线程池
     *
     * @return
     */
    public static ExecutorService getCacheThreadPool() {
        if (cacheThreadPool == null) {
            synchronized (ThreadPool.class) {
                if (cacheThreadPool == null) {
                    cacheThreadPool = Executors.newCachedThreadPool();
                }
            }
        }
        return cacheThreadPool;
    }

    /**
     * 单线程池
     *
     * @return
     */
    public static ExecutorService getSingleThreadPool() {
        if (singleThreadPool == null) {
            synchronized (ThreadPool.class) {
                if (singleThreadPool == null) {
                    singleThreadPool = Executors.newSingleThreadExecutor();
                }
            }
        }
        return singleThreadPool;
    }
}
