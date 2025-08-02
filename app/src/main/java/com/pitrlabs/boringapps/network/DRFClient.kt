package com.pitrlabs.boringapps.network

import com.squareup.moshi.Json
import com.squareup.moshi.Moshi
import retrofit2.Response
import retrofit2.Retrofit
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST

interface DRFClient {
    @POST("weight/")
    suspend fun predictWeight(@Body body: WeightRequest): Response<WeightResponse>
}

object RetrofitInstance {
    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    val api: DRFClient by lazy {
        Retrofit.Builder()
            .baseUrl("https://drf.pitrlabs.com/")
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(DRFClient::class.java)
    }
}

data class WeightRequest(
    val Height: Int?,
    val Gender: String?
)

data class WeightResponse(
    @Json(name = "Predicted Weight (kg)")
    val predictedWeight: Double
)
