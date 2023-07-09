package com.raunits.wallpapermanager.api

import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.json.JSONObject
import java.io.IOException

interface GetCategoriesCallback {
    fun onSuccess(categories: List<String>)
    fun onFailure(error: IOException)
}

fun getCategories(callback: GetCategoriesCallback) {
    val client = OkHttpClient()
    val request = Request.Builder()
        .url("https://api.cloudinary.com/v1_1/daa4wqa2h/folders/walls")
        .addHeader(
            "Authorization",
            "Basic ODkxNTk0Mzk5NzY1NjI5OmZhMjVQaFdqR0NGVHFIN2lfYTZEazh1Q0wwSQ=="
        )
        .build()

    println("START --- getCategories")

    val categories = mutableListOf<String>();

    client.newCall(request).enqueue(object : Callback {
        override fun onFailure(call: Call, e: IOException) {
            e.printStackTrace()
        }

        override fun onResponse(call: Call, response: Response) {
            response.use {
                if (!response.isSuccessful) throw IOException("Unexpected code $response")

                val responseBody = response.body?.string()

                val json = JSONObject(responseBody)
                val jsonArray = json.getJSONArray("folders")

                for (i in 0 until jsonArray.length()) {
                    val jsonObject = jsonArray.getJSONObject(i)
                    val category = jsonObject.getString("name")
                    categories.add(category);
                }

                callback.onSuccess(categories)
            }
        }
    })
}