package com.groww.papertrading.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.groww.papertrading.data.models.Portfolio
import com.groww.papertrading.data.repository.PortfolioRepository
import com.groww.papertrading.presentation.ui.state.PortfolioScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PortfolioViewModel @Inject constructor(
    private val portfolioRepository: PortfolioRepository
) : ViewModel() {

    private val _state = MutableStateFlow(PortfolioScreenState())
    val state: StateFlow<PortfolioScreenState> = _state.asStateFlow()

    fun loadPortfolio(userId: String) {
        _state.update { it.copy(isLoading = true) }
        
        viewModelScope.launch {
            try {
                portfolioRepository.getPortfolioFlow(userId).collect { portfolio ->
                    if (portfolio != null) {
                        val dayChange = portfolio.totalPnl
                        _state.update { 
                            it.copy(
                                portfolio = portfolio,
                                totalValue = portfolio.currentValue,
                                dayChange = dayChange,
                                isLoading = false
                            )
                        }
                    }
                }
            } catch (e: Exception) {
                _state.update { it.copy(error = e.message, isLoading = false) }
            }
        }
    }

    fun refreshPortfolio(userId: String) {
        loadPortfolio(userId)
    }
}
