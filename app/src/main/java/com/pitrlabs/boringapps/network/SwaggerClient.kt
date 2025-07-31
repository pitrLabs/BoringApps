package com.pitrlabs.boringapps.network

import com.pitrlabs.boringapps.model.ImageClassificationResult
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface SwaggerClient {
    @Multipart
    @POST("api/predict/image")
    suspend fun predictImage(
        @Part file: MultipartBody.Part
    ): Response<List<ImageClassificationResult>>
}
