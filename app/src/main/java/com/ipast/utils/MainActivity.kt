package com.ipast.utils

import android.Manifest
import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.ipast.utils.file.FileUtil
import com.ipast.utils.log.LogUtil
import com.tbruyelle.rxpermissions2.RxPermissions
import java.io.File

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
        //ToastUtil.showLong(this@MainActivity,"111")

    }

    @RequiresApi(Build.VERSION_CODES.N)
    @SuppressLint("CheckResult")
    fun writeLog(view: View) {
        val rxPermissions = RxPermissions(this)
        rxPermissions.request(*permissions)
            .subscribe { granted: Boolean ->
                if (granted) {

                    val now = System.currentTimeMillis();
                    val filename = FileUtil.getFileNamePrefix() + "_test.txt"
                    val path =
                        externalCacheDir!!.path + File.separator + filename
                    LogUtil.i("开始创建文件:$path")

                   /* FileUtils.createBigDigitalFile(
                        path,
                        256,
                        object : OnFileCreateCallback {
                            override fun onCreateSuccess(f: File?) {
                                val end = System.currentTimeMillis()
                                val duration = end - now
                                LogUtil.i(
                                    "duration:" + duration
                                )
                            }

                            override fun onCreateFailed() {
                                LogUtil.i("onCreateFailed")
                            }

                        })*/

                    /* LogUtil.i(
                         "length:" + FileUtil.createDigitalFileN(
                             path, 256
                         )
                     )*/
                    /* val end = System.currentTimeMillis()
                     val duration = end - now
                     LogUtil.i("duration:" + duration / 1000)*/

                }
            }
    }

}