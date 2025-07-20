package com.pitrlabs.boringapps.network

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.network.okHttpClient
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

object ApolloClientInstance {
    val client: ApolloClient by lazy {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()

        ApolloClient.Builder()
            .serverUrl("https://playground.pitrlabs.com/graphql/")
            .okHttpClient(okHttpClient)
            .build()
    }
}