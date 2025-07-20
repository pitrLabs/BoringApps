package com.pitrlabs.boringapps.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pitrlabs.boringapps.network.ApolloClientInstance
import com.pitrlabs.boringapps.service.ElementService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ElementViewModel(
    private val elementService: ElementService = ElementService(ApolloClientInstance.client)
) : ViewModel() {

    private val _uiState = MutableStateFlow(ElementUiState())
    val uiState: StateFlow<ElementUiState> = _uiState

    fun loadElements() {
        Log.d("ElementViewModel", "loadElements called")
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)
            try {
                Log.d("ElementViewModel", "Calling elementService.getElementList()")
                val elements = elementService.getElementList()
                Log.d("ElementViewModel", "Received ${elements.size} elements")
                _uiState.value = ElementUiState(elements = elements)
            } catch (e: Exception) {
                Log.e("ElementViewModel", "Error loading elements", e)
                _uiState.value = ElementUiState(error = e.message ?: "Unknown error")
            }
        }
    }
}