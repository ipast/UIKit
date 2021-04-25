package com.ipast.utils.screen

import android.app.Activity
import android.content.ContentProvider
import android.content.ContentResolver
import android.provider.Settings
import android.view.WindowManager

/**
 * @author gang.cheng
 * @description :
 * @date :2021/4/21
 */
object ScreenBrightUtil {
    /**
     *修改当前App界面屏幕亮度
     * @param context Activity
     * @param brightness Int 范围:0-255
     */
    fun setBrightness(context: Activity, brightness: Int) {
        val window = context.window
        val lp = window.attributes
        if (brightness == -1) {
            lp.screenBrightness = WindowManager.LayoutParams.BRIGHTNESS_OVERRIDE_NONE
        } else {
            when {
                brightness <= 0 -> {
                    lp.screenBrightness = 0f
                }
                brightness > 255 -> {
                    lp.screenBrightness = 1f
                }
                else -> {
                    lp.screenBrightness = brightness / 255f
                }
            }
        }
        window.attributes = lp
    }

    /**
     *获取系统屏幕亮度
     * @param context Activity
     */
    fun getBrightness(context: Activity): Int {
        val cr: ContentResolver? = context.contentResolver
        return Settings.System.getInt(cr, Settings.System.SCREEN_BRIGHTNESS)
    }
}