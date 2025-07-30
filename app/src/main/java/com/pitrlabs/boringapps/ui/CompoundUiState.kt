package com.pitrlabs.boringapps.ui

import com.pitrlabs.boringapps.model.Compound

data class CompoundUiState(
    val isLoading: Boolean = false,
    val compounds: List<Compound> = emptyList(),
    val error: String? = null
)
