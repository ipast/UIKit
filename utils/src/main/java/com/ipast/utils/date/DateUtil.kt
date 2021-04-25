package com.ipast.utils.date

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*



/**
 * @author gang.cheng
 * @description :
 * @date :2021/4/16
 */
object DateUtil {

    const val FORMAT_DATE_TIME = "yyyy-MM-dd HH:mm:ss"
    const val FORMAT_DATE_TIME_TZ = "yyyyMMdd'T'HHmmss'Z'"
    const val FORMAT_DATE_TIME_UTIL_MINUTE = "yyyy年MM月dd日 HH:mm"
    const val FORMAT_DATE_TIME_MISS_YEAR_SECOND = "MM月dd日 HH:mm"
    const val FORMAT_DATE_SHOW_WEEK = "yyyy年MM月dd日 E"
    const val FORMAT_DATE_SHOW_WEEK_PARENTHESES = "yyyy年MM月dd日 (E)"
    const val FORMAT_DATE_UNIT = "yyyy年MM月dd日"
    const val FORMAT_DATE_DIAGONAL = "yyyy/MM/dd"
    const val FORMAT_DATE_ = "yyyy-MM-dd"
    const val FORMAT_DATE = "yyyyMMdd"
    const val FORMAT_TIME_UTIL_MINUTE = "HH:mm"
    const val FORMAT_TIME = "HH:mm:ss"

    const val MILLISECOND_PER_DAY = 86400000L//每天的毫秒数
    const val DAYS_PER_WEEK = 7L//每周的天数
    const val MILLISECONDS_PER_HOUR = 3600000L//每小时的毫秒数
    const val SECONDS_PER_MINUTE = 60L//每分钟秒数
    const val SECONDS_PER_HOUR = 3600L//每小时秒数
    const val SECONDS_PER_DAY = 86400L//每天秒数
    const val ECONDS_PER_MONTH = 2592000L//每个月秒数，默认每月30天
    const val SECONDS_PER_YEAR = 31536000L//每年秒数，默认每年365天

    /**
     * 获取当前时间的毫秒数
     * @return Long
     */
    @JvmStatic
    fun getMillis(): Long {
        return System.currentTimeMillis()
    }

    /**
     * 获取当前时间字符串
     * @return String
     */
    @JvmStatic
    fun getTime(): String {
        return getDate(Date(), FORMAT_TIME)
    }

    /**
     * 获取当前日期和时间字符串
     * @return String
     */
    @JvmStatic
    fun getDateTime(): String {
        return getDate(Date(), FORMAT_DATE_TIME)
    }

    /**
     * 获取当前日期字符串
     * @return String
     */
    @JvmStatic
    fun getDate(): String {
        return getDate(Date(), FORMAT_DATE_)
    }

    /**
     * 获取指定日期的字符串格式
     * @param pattern String
     * @return String
     */
    @JvmStatic
    fun getDate(pattern: String): String {
        return formatDate(Date(), pattern)
    }

    /**
     * 获取指定日期的字符串格式
     * @param millis Long
     * @param pattern String
     * @return String
     */
    @JvmStatic
    @SuppressLint("SimpleDateFormat")
    fun getDate(millis: Long, pattern: String): String {
        return formatDate(millis, pattern)
    }

    /**
     * 获取指定日期的字符串格式
     * @param date Date
     * @param pattern String
     * @return String
     */
    @JvmStatic
    @SuppressLint("SimpleDateFormat")
    fun getDate(date: Date, pattern: String): String {
        return formatDate(date, pattern)
    }

    /**
     * 获取当前日期的年份
     * @return String
     */
    @JvmStatic
    fun getYear(): String {
        return getCalendar(Calendar.YEAR).toString()
    }

    /**
     * 获取当前日期的月份
     * @return String
     */
    @JvmStatic
    fun getMonth(): String {
        return (getCalendar(Calendar.MONTH) + 1).toString()
    }

    /**
     * 获取当前日期的月份
     * @return String
     */
    @JvmStatic
    fun getMonthDouble(): String {
        return (getCalendar(Calendar.MONTH) + 1).toString()
    }

    /**
     * 获取当前月份的日期
     * @return String
     */
    @JvmStatic
    fun getDay(): String {
        return getCalendar(Calendar.DAY_OF_MONTH).toString()
    }

    /**
     *
     * @param field Int
     * @return Int
     */
    @JvmStatic
    fun getCalendar(field: Int): Int {
        return Calendar.getInstance().get(field)
    }

    /**
     * 获取周几
     * @return String
     */
    @JvmStatic
    fun getWeek(): String {
        return formatDate("E")
    }

    /**
     * 格式化当前日期
     * @param pattern String
     * @return String
     */
    @JvmStatic
    @SuppressLint("SimpleDateFormat")
    fun formatDate(pattern: String): String {
        val format = SimpleDateFormat(pattern)
        return format.format(Date())
    }

    /**
     * 格式化当前日期
     * @param date Date
     * @param pattern String
     * @return String
     */
    @JvmStatic
    @SuppressLint("SimpleDateFormat")
    fun formatDate(date: Date, pattern: String): String {
        val format = SimpleDateFormat(pattern)
        return format.format(date)
    }

    /**
     * 格式化当前日期
     * @param millis Long
     * @param pattern String
     * @return String
     */
    @JvmStatic
    @SuppressLint("SimpleDateFormat")
    fun formatDate(millis: Long, pattern: String): String {
        val format = SimpleDateFormat(pattern)
        return format.format(millis)
    }

    /**
     * 是否是今天
     * @param millis Long
     * @return Boolean
     */
    @JvmStatic
    fun isToady(millis: Long): Boolean {
        val c1 = Calendar.getInstance()
        c1.timeInMillis = millis
        val c2 = Calendar.getInstance()
        return (c1[Calendar.YEAR] == c2[Calendar.YEAR]
                && c1[Calendar.MONTH] == c2[Calendar.MONTH]
                && c1[Calendar.DAY_OF_MONTH] == c2[Calendar.DAY_OF_MONTH]
                )
    }

    /**
     * 获取当前日期指定天数之后的日期.
     * @param num   相隔天数
     * @return Date 日期
     */
    @JvmStatic
    fun nextDay(num: Int): Date {
        val curr = Calendar.getInstance()
        curr[Calendar.DAY_OF_MONTH] = curr[Calendar.DAY_OF_MONTH] + num
        return curr.time
    }

    /**
     * 获取当前日期与指定日期相隔的天数.
     * @param date 给定的日期
     * @return long 日期间隔天数，正数表示给定日期在当前日期之前，负数表示在当前日期之后
     */
    @JvmStatic
    fun pastDays(date: Date): Long {
        val currentMillis = getMillis()
        return currentMillis - date.time / MILLISECOND_PER_DAY
    }

    /**
     * 获取当前日期指定月数之后的日期.
     * @param num   间隔月数
     * @return Date 日期
     */
    @JvmStatic
    fun nextMonth(num: Int): Date {
        val curr = Calendar.getInstance()
        curr[Calendar.MONTH] = curr[Calendar.MONTH] + num
        return curr.time
    }

    /**
     * 获取当前日期指定年数之后的日期.
     * @param num    间隔年数
     * @return Date 日期
     */
    @JvmStatic
    fun nextYear(num: Int): Date {
        val curr = Calendar.getInstance()
        curr[Calendar.YEAR] = curr[Calendar.YEAR] + num
        return curr.time
    }

    /**
     * 将 Date 日期转化为 Calendar 类型日期.
     * @param date
     * @return Calendar Calendar对象
     */
    @JvmStatic
    fun getCalendar(date: Date): Calendar {
        val calendar = Calendar.getInstance()
        calendar.time = date
        return calendar
    }

}