package com.groww.papertrading.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.groww.papertrading.data.models.Trade
import com.groww.papertrading.data.repository.TradeRepository
import com.groww.papertrading.presentation.ui.state.TradeHistoryScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TradeHistoryViewModel @Inject constructor(
    private val tradeRepository: TradeRepository
) : ViewModel() {

    private val _state = MutableStateFlow(TradeHistoryScreenState())
    val state: StateFlow<TradeHistoryScreenState> = _state.asStateFlow()

    fun loadTradeHistory(userId: String) {
        _state.update { it.copy(isLoading = true) }
        
        viewModelScope.launch {
            try {
                tradeRepository.getTradesFlow(userId).collect { trades ->
                    _state.update { 
                        it.copy(
                            trades = trades,
                            filteredTrades = trades,
                            isLoading = false
                        )
                    }
                }
            } catch (e: Exception) {
                _state.update { it.copy(error = e.message, isLoading = false) }
            }
        }
    }

    fun filterBySymbol(symbol: String?) {
        _state.update { state ->
            val filtered = if (symbol != null) {
                state.trades.filter { it.symbol == symbol }
            } else {
                state.trades
            }
            state.copy(selectedSymbol = symbol, filteredTrades = filtered)
        }
    }
}
