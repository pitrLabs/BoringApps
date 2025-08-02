package com.pitrlabs.boringapps.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pitrlabs.boringapps.network.WeightRequest
import com.pitrlabs.boringapps.network.RetrofitInstance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class WeightViewModel : ViewModel() {
    private val _weight = MutableStateFlow<Double?>(null)
    val weight = _weight.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error = _error.asStateFlow()

    fun predict(height: Int, gender: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            _weight.value = null

            try {
                val response = RetrofitInstance.api.predictWeight(
                    WeightRequest(Height = height, Gender = gender)
                )
                if (response.isSuccessful) {
                    _weight.value = response.body()?.predictedWeight
                } else {
                    _error.value = "Unsuccessful: ${response.code()}"
                }
            } catch (e: Exception) {
                _error.value = e.message
            } finally {
                _isLoading.value = false
            }
        }
    }
}
