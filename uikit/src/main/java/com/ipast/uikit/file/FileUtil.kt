package com.ipast.uikit.file

import android.text.TextUtils
import android.webkit.MimeTypeMap
import java.io.*
import java.nio.ByteBuffer
import java.nio.channels.FileChannel
import java.text.SimpleDateFormat
import java.util.*

/**
 * @author gang.cheng
 * @description :
 * @date :2021/4/16
 */
object FileUtil {


    /**
     * 判断文件是否存在
     * @param path String?
     * @return Boolean
     */
    fun isFileExist(path: String?): Boolean {
        return !TextUtils.isEmpty(path) && File(path).exists()
    }

    /**
     * 获取文件长度
     * @param srcPath String
     * @return Long
     */
    fun getFileLength(srcPath: String?): Long {
        if (TextUtils.isEmpty(srcPath)) {
            return -1
        }
        val srcFile = File(srcPath)
        return if (!srcFile.exists()) {
            -1
        } else srcFile.length()

    }

    /**
     * 获取文件扩展名
     *
     * @param filename
     * @return
     */
    fun getExtensionName(filename: String?): String {
        if (filename != null && filename.isNotEmpty()) {
            val dot = filename.lastIndexOf('.')
            if (dot > -1 && dot < filename.length - 1) {
                return filename.substring(dot + 1)
            }
        }
        return ""
    }

    /**
     * 获取文件类型
     *
     * @param filePath
     * @return
     */
    fun getMimeType(filePath: String): String {
        var type: String = ""
        if (TextUtils.isEmpty(filePath)) {
            return type
        }
        val extension: String = getExtensionName(filePath.toLowerCase())
        if (!TextUtils.isEmpty(extension)) {
            val mime = MimeTypeMap.getSingleton()
            type = mime.getMimeTypeFromExtension(extension)!!
        }
        if (TextUtils.isEmpty(type) && filePath.endsWith("aac")) {
            type = "audio/aac"
        }
        return type
    }

    /**
     * 将文件复制到指定路径
     *
     * @param srcPath
     * @param dstPath
     * @return
     */
    fun copy(srcPath: String, dstPath: String): Long {
        if (TextUtils.isEmpty(srcPath) || TextUtils.isEmpty(dstPath)) {
            return -1
        }
        val source = File(srcPath)
        if (!source.exists()) {
            return -1
        }
        if (srcPath == dstPath) {
            return source.length()
        }
        var fcin: FileChannel? = null
        var fcout: FileChannel? = null
        try {
            fcin = FileInputStream(source).channel
            fcout = FileOutputStream(create(dstPath)).channel
            val tmpBuffer = ByteBuffer.allocateDirect(4096)
            while (fcin.read(tmpBuffer) != -1) {
                tmpBuffer.flip()
                fcout.write(tmpBuffer)
                tmpBuffer.clear()
            }
            return source.length()
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            try {
                fcin?.close()
                fcout?.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        return -1
    }

    /**
     * @param path
     * @param content
     * @return
     */
    fun save(path: String?, content: String): Long {
        return save(content.toByteArray(), path)
    }

    /**
     * 把数据保存到文件系统中，并且返回其大小
     *
     * @param data
     * @param filePath
     * @return 如果保存失败, 则返回-1
     */
    fun save(data: ByteArray?, filePath: String?): Long {
        if (TextUtils.isEmpty(filePath)) {
            return -1
        }
        val f = File(filePath)
        if (f.parentFile == null) {
            return -1
        }
        if (!f.parentFile.exists()) { // 如果不存在上级文件夹
            f.parentFile.mkdirs()
        }
        try {
            f.createNewFile()
            val fout = FileOutputStream(f)
            fout.write(data)
            fout.close()
        } catch (e: IOException) {
            e.printStackTrace()
            return -1
        }
        return f.length()
    }

    /**
     * 文件移动
     * @param srcFilePath
     * @param dstFilePath
     * @return
     */
    fun move(srcFilePath: String?, dstFilePath: String?): Boolean {
        if (TextUtils.isEmpty(srcFilePath) || TextUtils.isEmpty(dstFilePath)) {
            return false
        }
        val srcFile = File(srcFilePath)
        if (!srcFile.exists() || !srcFile.isFile) {
            return false
        }
        val dstFile = File(dstFilePath)
        if (dstFile.parentFile == null) {
            return false
        }
        if (!dstFile.parentFile.exists()) { // 如果不存在上级文件夹
            dstFile.parentFile.mkdirs()
        }
        return srcFile.renameTo(dstFile)
    }

    /**
     * 创建文件
     * @param filePath
     * @return
     */
    fun create(filePath: String?): File? {
        if (TextUtils.isEmpty(filePath)) {
            return null
        }
        val f = File(filePath)
        if (!f.parentFile.exists()) { // 如果不存在上级文件夹
            f.parentFile.mkdirs()
        }
        return try {
            f.createNewFile()
            f
        } catch (e: IOException) {
            if (f != null && f.exists()) {
                f.delete()
            }
            null
        }
    }

    /**
     * 删除指定文件
     *
     * @param filePath 文件path
     * @return 删除结果
     */
    fun delFile(filePath: String?): Boolean {
        if (TextUtils.isEmpty(filePath))
            return false
        val file = File(filePath)
        if (file.exists()) {
            return file.delete()
        }
        return false
    }

    /**
     * 删除指定文件夹下所有文件
     *
     * @param path 文件夹完整绝对路径
     * @return 删除结果
     */
    fun delAllFile(path: String?): Boolean {
        var flag = false
        if (TextUtils.isEmpty(path)) {
            return flag
        }
        val file = File(path)
        if (!file.exists()) {
            return flag
        }
        if (!file.isDirectory) {
            return flag
        }
        val tempList = file.list()
        var temp: File? = null
        for (i in tempList.indices) {
            if (path!!.endsWith(File.separator)) {
                temp = File(path + tempList[i])
            } else {
                temp = File(path + File.separator + tempList[i])
            }
            if (temp.isFile) {
                temp.delete()
            } else {
                val folderPath = path + File.separator + tempList[i]
                delAllFile(folderPath)
                delFolder(folderPath)
                flag = true
            }
        }
        return flag
    }

    /**
     * 删除文件夹
     *
     * @param folderPath 文件夹完整绝对路径
     */
    fun delFolder(folderPath: String) {
        try {
            delAllFile(folderPath)
            val folder = File(folderPath)
            folder.delete()
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    /**
     * 获取随机文件名
     * @return
     */
    fun getRandomFileName(): String {
        val format = SimpleDateFormat("yyyyMMddHHmmss")
        val formatDate = format.format(Date())
        val random = Random().nextInt(100)
        return StringBuffer().append(formatDate).append(random).toString()
    }

}