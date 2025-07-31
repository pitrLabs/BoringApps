package com.pitrlabs.boringapps.model

import com.google.gson.annotations.SerializedName

data class ImageClassificationResult(
    @SerializedName("class")
    val clazz: String,
    val confidence: String
)
