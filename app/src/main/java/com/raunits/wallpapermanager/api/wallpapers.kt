package com.raunits.wallpapermanager.api

import okhttp3.Call
import okhttp3.Callback
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import org.json.JSONObject
import java.io.IOException

interface GetWallpapersByCategoryCallback {
    fun onSuccess(images: List<String>)
    fun onFailure(error: IOException)
}

fun getWallpapersByCategory(category: String, callback: GetWallpapersByCategoryCallback) {
    val client = OkHttpClient()
    val mediaType = "application/json".toMediaType()
    val body = "{\"expression\": \"folder=walls/$category\"}".toRequestBody(mediaType)
    val request = Request.Builder()
        .url("https://api.cloudinary.com/v1_1/daa4wqa2h/resources/search")
        .post(body)
        .addHeader("Content-Type", "application/json")
        .addHeader("Authorization", "Basic ODkxNTk0Mzk5NzY1NjI5OmZhMjVQaFdqR0NGVHFIN2lfYTZEazh1Q0wwSQ==")
        .build()

    println("START --- getWallpapersByCategory")

    val images = mutableListOf<String>();

    client.newCall(request).enqueue(object : Callback {
        override fun onFailure(call: Call, e: IOException) {
            e.printStackTrace()
        }

        override fun onResponse(call: Call, response: Response) {
            response.use {
                if (!response.isSuccessful) throw IOException("Unexpected code $response")

                val responseBody = response.body?.string()

                val json = JSONObject(responseBody)
                val jsonArray = json.getJSONArray("resources")

                for (i in 0 until jsonArray.length()) {
                    val jsonObject = jsonArray.getJSONObject(i)
                    val img = jsonObject.getString("secure_url")
                    images.add(img);
                }

                callback.onSuccess(images)
            }
        }
    })
}