package com.pitrlabs.boringapps.ui

import com.pitrlabs.boringapps.model.Element

data class ElementUiState(
    val isLoading: Boolean = false,
    val elements: List<Element> = emptyList(),
    val error: String? = null
)
