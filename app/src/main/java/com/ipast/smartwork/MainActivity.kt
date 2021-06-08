package com.ipast.smartwork

import android.Manifest
import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.ipast.utils.date.DateUtil
import com.ipast.utils.file.FileUtil
import com.ipast.utils.log.LogUtil
import com.ipast.utils.screen.ScreenBrightUtil
import com.ipast.utils.toast.ToastUtil
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

    @SuppressLint("CheckResult")
    fun writeLog(view: View) {
        val rxPermissions = RxPermissions(this)
        rxPermissions.request(*permissions)
            .subscribe { granted: Boolean ->
                if (granted) {
                    Thread(object : Runnable {
                        override fun run() {
                            // ToastUtil.showLong(this@MainActivity,"111")
                            val filename = FileUtil.getFileNamePrefix() + "_test.txt"
                            val path = externalCacheDir!!.path + File.separator + "file" + File.separator + filename
                            LogUtil.i("length:"+FileUtil.createDigitalFile(path,1024))
                        }

                    }).start()
                }
            }
    }

    private fun save() {
        val filename = FileUtil.getFileNamePrefix() + "_log.txt"
        val path =
            this.externalCacheDir!!.path + File.separator + "file" + File.separator + filename
        var sb: StringBuffer = StringBuffer()
            .append(checkStrLength("time", 19))
            .append(tab)
            .append(checkStrLength("writing speed", 15))
            .append(tab)
            .append(checkStrLength("writing times", 15))
            .append(tab)
            .append(checkStrLength("reading speed", 15))
            .append(tab)
            .append(checkStrLength("reading times", 15))
            .append(enter)
            .append("--------------------------------------------------------------------------------------")
        FileUtil.append(path, sb.toString())
        sb = StringBuffer()
            .append(enter)
            .append(checkStrLength(DateUtil.getDateTime(), 19))
            .append(tab)
            .append(checkStrLength("100KB/s", 15))
            .append(tab)
            .append(checkStrLength("100", 15))
            .append(tab)
            .append(checkStrLength("200KB/s", 15))
            .append(tab)
            .append(checkStrLength("99", 15))

        FileUtil.append(path, sb.toString())
        sb = StringBuffer()
            .append(enter)
            .append(checkStrLength(DateUtil.getDateTime(), 19))
            .append(tab)
            .append(checkStrLength("101KB/s", 15))
            .append(tab)
            .append(checkStrLength("101", 15))
            .append(tab)
            .append(checkStrLength("201KB/s", 15))
            .append(tab)
            .append(checkStrLength("100", 15))

        FileUtil.append(path, sb.toString())
    }

    private fun checkStrLength(str: String, len: Int): String {
        val temp: Int = len - str.length
        var result = str
        if (temp > 0) {
            for (i in 0 until temp) {
                result = "$result "
            }
        }
        return result
    }
}