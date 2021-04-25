package com.ipast.utils.log

import android.text.TextUtils
import android.util.Log

/**
 * @author gang.cheng
 * @description :
 * @date :2021/4/15
 */
object LogUtil {
    private val TAG = LogUtil::class.simpleName
    private var isDebug = true

    /**
     * 设置是否为debug模式，debug模式下打印日志
     *
     * @param isDebug
     */
    @JvmStatic
    fun setIsDebug(isDebug: Boolean) {
        LogUtil.isDebug = isDebug
    }

    /**
     * 创建日志消息头
     *
     * @return
     */
    private fun createLogHeader(): String {
        val stackTraceElements = Thread.currentThread().stackTrace
        val length = stackTraceElements.size
        var i = length - 1
        while (i >= 0) {
            var element = stackTraceElements[i]
            if (element.className == LogUtil::class.java.name) {
                i++
                element = stackTraceElements[i]
                return String.format(
                    "%s.%s(%s:%d):",
                    element.className,
                    element.methodName,
                    element.fileName,
                    element.lineNumber
                )
            }
            i--
        }
        return ""
    }

    /**
     * 创建日志
     *
     * @param header
     * @param log
     * @return
     */
    private fun createLog(header: String, log: String): String {
        return "$header\n================================\n$log\n================================"
    }

    /**
     * 日志（错误）
     * @param msg
     */
    @JvmStatic
    fun e(msg: String) {
        if (isDebug) {
            val logHeader = createLogHeader()
            if (!TextUtils.isEmpty(logHeader)) {
                Log.e(TAG, createLog(logHeader, msg))
            }
        }
    }

    /**
     * 日志（信息）
     * @param msg
     */
    @JvmStatic
    fun i(msg: String) {
        if (isDebug) {
            val logHeader = createLogHeader()
            if (!TextUtils.isEmpty(logHeader)) {
                Log.i(TAG, createLog(logHeader, msg))
            }
        }
    }

    /**
     * 日志（调试）
     * @param msg
     */
    @JvmStatic
    fun d(msg: String) {
        if (isDebug) {
            val logHeader = createLogHeader()
            if (!TextUtils.isEmpty(logHeader)) {
                Log.d(TAG, createLog(logHeader, msg))
            }
        }
    }

    /**
     * 日志（详细）
     * @param msg
     */
    @JvmStatic
    fun v(msg: String) {
        if (isDebug) {
            val logHeader = createLogHeader()
            if (!TextUtils.isEmpty(logHeader)) {
                Log.v(TAG, createLog(logHeader, msg))
            }
        }
    }

    /**
     * 日志（警告）
     * @param msg
     */
    @JvmStatic
    fun w(msg: String) {
        if (isDebug) {
            val logHeader = createLogHeader()
            if (!TextUtils.isEmpty(logHeader)) {
                Log.w(TAG, createLog(logHeader, msg))
            }
        }
    }
}