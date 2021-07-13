package com.ipast.utils

import android.Manifest
import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.ipast.utils.ecrash.CrashHandler
import com.ipast.utils.file.FileUtil
import com.ipast.utils.log.LogUtil
import com.tbruyelle.rxpermissions2.RxPermissions
import java.io.File
import java.lang.NullPointerException

class MainActivity : AppCompatActivity() {
    val tab = "\t"
    val enter = "\r\n"
    private val permissions = arrayOf(
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        test()
    }

    fun throwException() {
        var tt:String ?= null
        Handler().postDelayed(
            Runnable { tt!!.length},
            1000
        )

    }

    @SuppressLint("CheckResult")
    fun test() {
        val rxPermissions = RxPermissions(this)
        rxPermissions.request(*permissions)
            .subscribe { granted: Boolean ->
                if (granted) {
                    //throwException()
                    CrashHandler.instance.handleException(Throwable("test crash handler"))
                }
            }
    }

}