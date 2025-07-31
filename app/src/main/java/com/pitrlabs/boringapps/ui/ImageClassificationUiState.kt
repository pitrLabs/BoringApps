package com.pitrlabs.boringapps.ui

import com.pitrlabs.boringapps.model.ImageClassificationResult

data class ImageClassificationUiState(
    val isLoading: Boolean = false,
    val results: List<ImageClassificationResult> = emptyList(),
    val error: String? = null
)
