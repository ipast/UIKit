package com.ipast.utils.service

import android.app.ActivityManager
import android.content.Context
import java.util.*

/**
 * @author gang.cheng
 * @description :
 * @date :2021/4/25
 */
object ServiceUtil {

    /**
     * @param service
     * @return 当前phone service是否在运行
     */
    @JvmStatic
    fun isRunning(context: Context, service: String): Boolean {
        val am = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val services =
            am.getRunningServices(Int.MAX_VALUE) as ArrayList<ActivityManager.RunningServiceInfo>
        for (i in services.indices) {
            if (services[i].service.className == service) return true
        }
        return false
    }
}