package com.pitrlabs.boringapps.service

import com.pitrlabs.boringapps.model.ImageClassificationResult
import com.pitrlabs.boringapps.network.SwaggerClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File

object ImageClassificationService {
    private val api: SwaggerClient by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://swagger.pitrlabs.com/")
            .client(OkHttpClient.Builder().build())
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofit.create(SwaggerClient::class.java)
    }

    suspend fun classifyImage(file: File): List<ImageClassificationResult>? {
        return withContext(Dispatchers.IO) {
            val requestFile = file.asRequestBody("image/*".toMediaTypeOrNull())
            val body = MultipartBody.Part.createFormData("file", file.name, requestFile)

            val response = api.predictImage(body)
            if (response.isSuccessful) response.body() else null
        }
    }
}
