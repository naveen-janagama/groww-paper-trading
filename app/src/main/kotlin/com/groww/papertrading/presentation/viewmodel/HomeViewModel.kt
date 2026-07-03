package com.groww.papertrading.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.groww.papertrading.data.models.Stock
import com.groww.papertrading.data.repository.StockRepository
import com.groww.papertrading.presentation.ui.state.HomeScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val stockRepository: StockRepository
) : ViewModel() {

    private val _state = MutableStateFlow(HomeScreenState())
    val state: StateFlow<HomeScreenState> = _state.asStateFlow()

    init {
        loadTopGainersAndLosers()
    }

    private fun loadTopGainersAndLosers() {
        _state.update { it.copy(isLoading = true) }
        
        viewModelScope.launch {
            try {
                stockRepository.getTopGainers().collect { gainers ->
                    _state.update { it.copy(topGainers = gainers) }
                }
            } catch (e: Exception) {
                _state.update { it.copy(error = e.message) }
            }
        }
        
        viewModelScope.launch {
            try {
                stockRepository.getTopLosers().collect { losers ->
                    _state.update { it.copy(topLosers = losers, isLoading = false) }
                }
            } catch (e: Exception) {
                _state.update { it.copy(error = e.message, isLoading = false) }
            }
        }
    }

    fun refreshData() {
        loadTopGainersAndLosers()
    }
}
