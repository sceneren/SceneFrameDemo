package wiki.scene.lib_network.util

import android.text.TextUtils
import okhttp3.*
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream

/**
 * Created by heavyrainlee on 20/02/2018.
 */
class DownloadManager private constructor() {
    private val okHttpClient: OkHttpClient = OkHttpClient()

    fun download(url: String, saveDir: String, name: String?, listener: OnDownloadListener) {
        val request: Request = Request.Builder().url(url).build()
        okHttpClient.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                // 下载失败
                listener.onFail()
            }

            @Throws(IOException::class)
            override fun onResponse(call: Call, response: Response) {
                var inputStream: InputStream? = null
                var fos: FileOutputStream? = null
                try {
                    val buf = ByteArray(2048)
                    var len = 0
                    // 储存下载文件的目录
                    val savePath = isExistDir(saveDir)
                    val fileName = if (TextUtils.isEmpty(name)) getNameFromUrl(url) else name!!
                    inputStream = response.body!!.byteStream()
                    val total = response.body!!.contentLength()
                    val file = File(savePath, fileName)
                    fos = FileOutputStream(file)
                    var sum: Long = 0
                    while (inputStream.read(buf).also { len = it } != -1) {
                        fos.write(buf, 0, len)
                        sum += len.toLong()
                        val progress = (sum * 1.0f / total * 100).toInt()
                        // 下载中
                        listener.onProgress(progress)
                    }
                    fos.flush()
                    // 下载完成
                    listener.onSuccess(file)
                } catch (e: Exception) {
                    listener.onFail()
                } finally {
                    try {
                        inputStream?.close()
                    } catch (e: IOException) {
                    }
                    try {
                        fos?.close()
                    } catch (e: IOException) {
                    }
                }
            }
        })
    }

    /**
     * @param saveDir
     * @return
     * @throws IOException 判断下载目录是否存在
     */
    @Throws(IOException::class)
    private fun isExistDir(saveDir: String): String {
        // 下载位置
        val downloadFile = File(saveDir)
        if (!downloadFile.mkdirs()) {
            downloadFile.createNewFile()
        }
        return downloadFile.absolutePath
    }

    /**
     * @param url
     * @return 从下载连接中解析出文件名
     */
    private fun getNameFromUrl(url: String): String {
        return url.substring(url.lastIndexOf("/") + 1)
    }

    interface OnDownloadListener {
        /**
         * 下载成功
         */
        fun onSuccess(file: File?)

        /**
         * @param progress 下载进度
         */
        fun onProgress(progress: Int)

        /**
         * 下载失败
         */
        fun onFail()
    }

    companion object {
        private var downloadManager: DownloadManager? = null
        fun get(): DownloadManager? {
            if (downloadManager == null) {
                downloadManager = DownloadManager()
            }
            return downloadManager
        }
    }

}