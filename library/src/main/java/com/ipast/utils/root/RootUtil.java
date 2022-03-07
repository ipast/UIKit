package com.ipast.utils.root;

import android.os.Build;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;

/**
 * @author gang.cheng
 * @description :
 * @date :2021/8/6
 */
public class RootUtil {

    /**
     * @return 设备是否root
     */
    public static boolean isRooted() {
        return checkRootMethod1() || checkRootMethod2() || checkRootMethod3();
    }

    private static boolean checkRootMethod1() {
        String buildTags = Build.TAGS;
        return buildTags != null && buildTags.contains("test-keys");
    }

    private static boolean checkRootMethod2() {
        String[] paths = new String[]{
                "/system/app/Superuser.apk",
                "/sbin/su",
                "/system/bin/su",
                "/system/xbin/su",
                "/data/local/xbin/su",
                "/data/local/bin/su",
                "/system/sd/xbin/su",
                "/system/bin/failsafe/su",
                "/data/local/su",
                "/su/bin/su"
        };
        for (String path : paths) {
            if ((new File(path)).exists()) {
                return true;
            }
        }
        return false;
    }

    private static boolean checkRootMethod3() {
        Process process = null;
        boolean isRooted = false;
        try {
            process = Runtime.getRuntime().exec(new String[]{"/system/xbin/which", "su"});
            BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));
            if (in.readLine() != null) {
                isRooted = true;
            }
        } catch (Throwable e) {
            isRooted = false;
        } finally {
            if (process != null) {
                process.destroy();
            }
            return isRooted;
        }
    }
}
