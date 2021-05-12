package com.ipast.utils.process;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Process;
import android.text.TextUtils;

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
     * @param context
     * @return
     */
    public static boolean isMainProcess(Context context) {
        if (context == null) {
            return false;
        } else {
            String packageName = context.getApplicationContext().getPackageName();
            String processName = getProcessName(context);
            return packageName.equals(processName);
        }
    }

    /**
     * @param context
     * @return
     */
    public static String getProcessName(Context context) {
        String processName;
        if ((processName = getProcessFromFile()) == null) {
            processName = getProcessNameByAM(context);
        }

        return processName;
    }

    private static String getProcessFromFile() {
        BufferedReader bufferedReader = null;
        boolean var = false;

        String process;
        label97:
        {
            try {
                var = true;
                int myPid = Process.myPid();
                process = "/proc/" + myPid + "/cmdline";
                bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(process), "iso-8859-1"));
                StringBuilder sb = new StringBuilder();

                while ((myPid = bufferedReader.read()) > 0) {
                    sb.append((char) myPid);
                }

                process = sb.toString();
                var = false;
                break label97;
            } catch (Exception exception) {
                var = false;
            } finally {
                if (var) {
                    if (bufferedReader != null) {
                        try {
                            bufferedReader.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                }
            }

            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            return null;
        }

        try {
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return process;
    }

    private static String getProcessNameByAM(Context context) {
        String processName = null;
        ActivityManager am;
        if ((am = (ActivityManager) context.getSystemService(ACTIVITY_SERVICE)) == null) {
            return null;
        } else {
            while (true) {
                List runningAppProcesses;
                if ((runningAppProcesses = am.getRunningAppProcesses()) != null) {
                    Iterator iterator = runningAppProcesses.iterator();

                    while (iterator.hasNext()) {
                        ActivityManager.RunningAppProcessInfo rapi;
                        if ((rapi = (ActivityManager.RunningAppProcessInfo) iterator.next()).pid == Process.myPid()) {
                            processName = rapi.processName;
                            break;
                        }
                    }
                }

                if (!TextUtils.isEmpty(processName)) {
                    return processName;
                }

                try {
                    Thread.sleep(100L);
                } catch (InterruptedException interruptedException) {
                    interruptedException.printStackTrace();
                }
            }
        }
    }

    /**
     * @param context
     * @return
     */
    public static boolean isMainProcessLive(Context context) {
        if (context == null) {
            return false;
        }

        String packageName = context.getPackageName();
        ActivityManager am;
        List runningAppProcesses;
        if ((am = (ActivityManager) context.getSystemService(ACTIVITY_SERVICE)) != null
                && (runningAppProcesses = am.getRunningAppProcesses()) != null) {
            Iterator iterator = runningAppProcesses.iterator();

            while (iterator.hasNext()) {
                if (((ActivityManager.RunningAppProcessInfo) iterator.next()).processName.equals(packageName)) {
                    return true;
                }
            }
        }

        return false;

    }


    /**
     * 当前应用是否在前台
     *
     * @param context
     * @return
     */
    public static boolean isMainProcessForeground(Context context) {
        if (context == null) {
            return false;
        }
        String packageName = context.getPackageName();
        ActivityManager am;
        List runningAppProcesses;

        if ((am = (ActivityManager) context.getSystemService(ACTIVITY_SERVICE)) != null
                && (runningAppProcesses = am.getRunningAppProcesses()) != null) {
            Iterator iterator = runningAppProcesses.iterator();
            while (iterator.hasNext()) {
                ActivityManager.RunningAppProcessInfo rapi;
                if ((rapi = (ActivityManager.RunningAppProcessInfo) iterator.next()).processName.equals(packageName)) {
                    if (rapi.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                        return true;
                    }
                }
            }
        }
        return false;
    }


}
