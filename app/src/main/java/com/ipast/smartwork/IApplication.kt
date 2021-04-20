package com.ipast.smartwork

import android.app.Application
import com.ipast.utils.ecrash.CrashHandler

/**
 * @author gang.cheng
 * @description :
 * @date :2021/4/19
 */
class IApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        CrashHandler.instance.init(this,true)
    }
}