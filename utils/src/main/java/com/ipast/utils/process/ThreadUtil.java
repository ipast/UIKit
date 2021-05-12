package com.ipast.utils.process;

import android.os.Looper;

/**
 * @author gang.cheng
 * @description :
 * @date :2021/5/12
 */
public class ThreadUtil {
    /**
     * @return 是否为主线程
     */
    public static boolean isMainThread() {
        return Thread.currentThread() == Looper.getMainLooper().getThread();
    }
}
