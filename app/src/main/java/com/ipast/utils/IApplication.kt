package com.ipast.utils

import android.app.Application
import android.os.Environment
import com.ipast.utils.ecrash.CrashHandler

/**
 * @author gang.cheng
 * @description :
 * @date :2021/4/19
 */
class IApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        val filepath: String = Environment.getExternalStorageDirectory().toString() + "/Utils"
        CrashHandler.instance.init(this,  true)
        CrashHandler.instance.setThrowException(true)
    }
}