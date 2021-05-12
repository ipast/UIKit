package com.ipast.utils.toast;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.view.Gravity;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;

import com.ipast.utils.process.ProcessUtil;
import com.ipast.utils.process.ThreadUtil;

/**
 * @author gang.cheng
 * @description :
 * @date :2021/5/12
 */
public class ToastUtil {

    private static Handler mHandler;
    private static Toast mToast;

    public static void cancel() {
        if (mHandler != null) {
            mHandler.removeCallbacks(null);
        }
        if (mToast != null) {
            mToast.cancel();
        }
    }

    private static  void postToMainThread(Runnable paramRunnable) {
        if (ThreadUtil.isMainThread()) {
            paramRunnable.run();
        } else {
            if (mHandler == null) {
                mHandler =  new Handler(Looper.getMainLooper());
            }
            mHandler.post(paramRunnable);
        }
    }

    private static void show(Context context, String text, int duration) {
        if (!ProcessUtil.isMainProcessForeground(context)) {
            return;
        }
        postToMainThread(() -> {
            if (mToast != null) {
                mToast.cancel();
            }
            mToast = Toast.makeText(context, " ", Toast.LENGTH_SHORT);
            mToast.setGravity(Gravity.CENTER, 0, 0);
            mToast.setText(text);
            mToast.setDuration(duration);
            mToast.show();
        });

    }

    public static void showLong(Context context, @StringRes int resId) {
        show(context, context.getResources().getString(resId), Toast.LENGTH_LONG);
    }

    public static void showLong(Context context, @NonNull String sting) {
        show(context, sting, Toast.LENGTH_LONG);
    }


    public static void showShort(Context context, @StringRes int resId) {
        show(context, context.getResources().getString(resId), Toast.LENGTH_SHORT);
    }

    public static void showShort(Context context, @NonNull String sting) {
        show(context, sting, Toast.LENGTH_SHORT);
    }
}
