package com.pitrlabs.boringapps.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pitrlabs.boringapps.service.ImageClassificationService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.io.File

class ImageClassificationViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(ImageClassificationUiState())
    val uiState: StateFlow<ImageClassificationUiState> = _uiState

    fun classifyImage(file: File) {
        _uiState.value = _uiState.value.copy(isLoading = true, error = null)

        viewModelScope.launch {
            try {
                val result = ImageClassificationService.classifyImage(file)
                if (result != null) {
                    _uiState.value = ImageClassificationUiState(results = result)
                } else {
                    _uiState.value = ImageClassificationUiState(error = "Fail")
                }
            } catch (e: Exception) {
                _uiState.value = ImageClassificationUiState(error = e.localizedMessage)
            }
        }
    }
}
