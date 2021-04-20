package com.ipast.utils.density

import android.annotation.SuppressLint
import android.content.Context
import android.util.TypedValue

/**
 * 尺寸转换工具
 */
@SuppressLint("StaticFieldLeak")
object DensityUtil {
    private var context: Context? = null
    fun init(context: Context) {
        DensityUtil.context = context.applicationContext
    }

    fun dp2px(value: Float): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            value, context!!.resources.displayMetrics
        ).toInt()
    }

    fun px2dp(value: Float): Int {
        val scale = context!!.resources.displayMetrics.density
        return (value / scale + 0.5f).toInt()
    }

    fun sp2px(value: Float): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_SP, value,
            context!!.resources.displayMetrics
        ).toInt()
    }

    fun px2sp(value: Float): Int {
        val fontScale = context!!.resources.displayMetrics.density
        return (value / fontScale + 0.5f).toInt()
    }
}