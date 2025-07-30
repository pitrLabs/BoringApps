package com.pitrlabs.boringapps.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pitrlabs.boringapps.network.ApolloClientInstance
import com.pitrlabs.boringapps.service.CompoundService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CompoundViewModel(
    private val compoundService: CompoundService = CompoundService(ApolloClientInstance.client)
) : ViewModel() {
    private val _uiState = MutableStateFlow(CompoundUiState())
    val uiState: StateFlow<CompoundUiState> = _uiState

    fun loadCompounds() {
        Log.d("CompoundViewModel", "loadCompounds called")
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)
            try {
                Log.d("CompoundViewModel", "Calling compoundService.getCompoundList()")
                val compounds = compoundService.getCompoundList()
                Log.d("CompoundViewModel", "Received ${compounds.size} compounds")
                _uiState.value = CompoundUiState(compounds = compounds)
            } catch (e: Exception) {
                Log.e("CompoundViewModel", "Error loading compounds", e)
                _uiState.value = CompoundUiState(error = e.message ?: "Unknown error")
            }
        }
    }
}