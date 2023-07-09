package com.raunits.wallpapermanager.components

import android.app.DownloadManager
import android.content.Context
import android.os.Environment
import androidx.core.net.toUri

interface Downloader {
    fun downloadImage(url: String, filename: String): Long
}

class AndroidDownloader(private val context: Context): Downloader {
    private val downloadManager = context.getSystemService(DownloadManager::class.java)

    override fun downloadImage(url: String, filename: String): Long {
        val mimeType = filename.split(".")[1]
        val request = DownloadManager
            .Request(url.toUri())
            .setMimeType("image/$mimeType")
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, filename)
        return downloadManager.enqueue(request)
    }
}