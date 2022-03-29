package com.ipast.utils.process;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Process;
import android.text.TextUtils;

import androidx.annotation.NonNull;

import com.ipast.utils.log.LogUtil;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import static android.content.Context.ACTIVITY_SERVICE;

/**
 * @author gang.cheng
 * @description :
 * @date :2021/5/12
 */
public class ProcessUtil {

    /**
     * 是否是主进程
     *
     * @param context
     * @return
     */
    public static boolean isMainProcess(Context context) {
        String processName = getMyProcessName(context);
        if (!TextUtils.isEmpty(processName) && processName.equals(context.getPackageName())) {
            return true;
        }
        return false;
    }


    /**
     * @param context 获取当前进程名称
     * @return
     */
    public static String getMyProcessName(Context context) {

        ActivityManager am = (ActivityManager) context.getSystemService(ACTIVITY_SERVICE);
        if (am == null) {
            return null;
        }
        List<ActivityManager.RunningAppProcessInfo> runningApps = am.getRunningAppProcesses();
        if (runningApps == null) {
            return null;
        }
        int pid = Process.myPid();
        for (ActivityManager.RunningAppProcessInfo procInfo : runningApps) {
            if (procInfo.pid == pid) {
                return procInfo.processName;
            }
        }

        return null;
    }

    /**
     * 判断当前应用主进程是否在运行
     *
     * @param context
     * @return
     */
    public static boolean isMainProcessLive(@NonNull Context context) {
        String processName = context.getPackageName();
        return isProcessLive(context, processName);
    }

    /**
     * 判断指定进程是否在运行
     *
     * @param context
     * @param processName
     * @return
     */
    public static boolean isProcessLive(@NonNull Context context, String processName) {
        return getRunningAppProcessInfo(context, processName) != null;
    }

    public static ActivityManager.RunningAppProcessInfo getRunningAppProcessInfo(Context context, String processName) {
        if (TextUtils.isEmpty(processName)) {
            return null;
        }
        ActivityManager am = (ActivityManager) context.getSystemService(ACTIVITY_SERVICE);
        if (am == null) {
            return null;
        }
        List<ActivityManager.RunningAppProcessInfo> runningApps = am.getRunningAppProcesses();
        if (runningApps == null) {
            return null;
        }
        for (ActivityManager.RunningAppProcessInfo procInfo : runningApps) {
            if (procInfo.processName.equals(processName)) {
                return procInfo;
            }
        }
        return null;
    }

    /**
     * 当前应用是否在前台
     *
     * @param context
     * @return
     */
    public static boolean isMainProcessForeground(Context context) {
        String processName = context.getPackageName();
        ActivityManager.RunningAppProcessInfo processInfo = getRunningAppProcessInfo(context, processName);
        if (processInfo == null) {
            return false;
        }
        if (processInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
            return true;
        }
        return false;
    }


}
