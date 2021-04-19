package com.ipast.uikit.string

import android.graphics.Paint
import android.graphics.Rect
import android.text.TextUtils
import java.math.BigDecimal
import java.util.regex.Pattern

/**
 * @author gang.cheng
 * @description :
 * @date :2021/4/16
 */
object StringUtil {
    /**
     * 判断字符串是否有值，如果为null或者是空字符串或者只有空格或者为"null"字符串，则返回true，否则则返回false
     */
    fun isEmpty(value: String): Boolean {
        if (value != null && !"".equals(value.trim(), true)
            && !"null".equals(value.trim(), true)
        ) {
            return false
        }
        return true
    }

    /**
     * 格式化单位
     *
     * @param size
     * @return
     */
    fun getFormatSize(size: Double): String {
        val kiloByte = size / 1024
        if (kiloByte < 1) {
            return "0K"
        }
        val megaByte = kiloByte / 1024
        if (megaByte < 1) {
            val result1 = kiloByte.toBigDecimal()
            return result1.setScale(2, BigDecimal.ROUND_HALF_UP)
                .toPlainString() + "KB"
        }
        val gigaByte = megaByte / 1024
        if (gigaByte < 1) {
            val result2 = megaByte.toBigDecimal()
            return result2.setScale(2, BigDecimal.ROUND_HALF_UP)
                .toPlainString() + "MB"
        }
        val teraBytes = gigaByte / 1024
        if (teraBytes < 1) {
            val result3 = gigaByte.toBigDecimal()
            return result3.setScale(2, BigDecimal.ROUND_HALF_UP)
                .toPlainString() + "GB"
        }
        val result4 = teraBytes.toBigDecimal()
        return (result4.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString()
                + "TB")
    }

    /**
     * 判断邮箱格式是否正确
     * @param email
     * @return
     */
    fun isEmail(email: String): Boolean {
        val str =
            "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$"
        val p = Pattern.compile(str)
        val m = p.matcher(email)
        return m.matches()
    }

    /**
     * 判断手机号码是否正确
     * @param phoneNumber
     * @return
     */
    fun isPhoneNumberValid(phoneNumber: String): Boolean {
        var isVaild = false
        val expression = "^1[3|4|5|6|7|8|9][0-9]\\\\d{8}\$"
        val pattern = Pattern.compile(expression)
        val matcher = pattern.matcher(phoneNumber)
        if (matcher.matches()) {
            isVaild = true
        }
        return isVaild
    }

    /**
     * 判断电话号码是否正确
     * @param areaCode String
     * @param phoneNumber String
     * @return Boolean
     */

    fun isPhoneNumberValid(areaCode: String, phoneNumber: String): Boolean {
        if (TextUtils.isEmpty(phoneNumber)) {
            return false
        }
        if (phoneNumber.length < 5) {
            return false
        }
        if (TextUtils.equals(areaCode, "+86") || TextUtils.equals(areaCode, "86")) {
            return isPhoneNumberValid(phoneNumber)
        }
        var isValid = false;
        val expression = "^[0-9]*$"
        val pattern = Pattern.compile(expression)
        val matcher = pattern.matcher(phoneNumber)
        if (matcher.matches()) {
            isValid = true
        }
        return isValid
    }


    /**
     * 验证是否是数字
     * @param number String
     * @return Boolean
     */

    fun isNumber(number: String): Boolean {
        return try {
            val p = Pattern.compile("\\d+")
            val m = p.matcher(number)
            m.matches()
        } catch (e: Exception) {
            false
        }

    }

    /**
     * 获取字符串长度（中文2个）
     * @param str String
     * @return Int
     */
    fun getStringLength(str: String?): Int {
        if (TextUtils.isEmpty(str)) {
            return 0
        }
        var count = 0
        val strArray = str!!.toCharArray()
        for (item in strArray) {
            count += if (item.toInt() < 128) {
                1
            } else {
                2
            }
        }
        return count
    }

    /**
     * 一个em单位的宽度等于行高的高度
     * @param str
     * @return 字符串ems
     */
    fun getEms(str: String?): Int {
        if (TextUtils.isEmpty(str)) {
            return 0
        }
        val paint = Paint()
        val rect = Rect()
        paint.getTextBounds(str, 0, str!!.length, rect)
        return rect.width() / rect.height()

    }
}