package com.ipast.uikit.date

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*

/**
 * @author gang.cheng
 * @description :
 * @date :2021/4/16
 */
object DateUtil {
    const val PATTERN_ALL = "yyyy-MM-dd HH:mm:ss"
    const val PATTERN_ALL_TZ = "yyyyMMdd'T'HHmmss'Z'"
    const val PATTERN_UTIL_MINUTE = "yyyy年MM月dd日 HH:mm"
    const val PATTERN_MISS_YEAR_SECOND = "MM月dd日 HH:mm"
    const val PATTERN_UTIL_WEEK = "yyyy年MM月dd日 E"
    const val PATTERN_UTIL_WEEK_PARENTHESES = "yyyy年MM月dd日 (E)"
    const val PATTERN_UTIL_DAY_UNIT = "yyyy年MM月dd日"
    const val PATTERN_UTIL_DAY_DIAGONAL = "yyyy/MM/dd"
    const val PATTERN_UTIL_DAY= "yyyyMMdd"
    const val PATTERN_HOUR_MINUTE = "HH:mm"

    /**
     * 时间格式化
     *
     * @param millis
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    fun formatMillis(millis: Long, pattern: String): String {
        val format = SimpleDateFormat(pattern)
        return format.format(millis)
    }

    /**
     * 时间格式化
     *
     * @param date
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    fun formatDate(date: Date, pattern: String): String {
        val format = SimpleDateFormat(pattern)
        return format.format(date)
    }
}