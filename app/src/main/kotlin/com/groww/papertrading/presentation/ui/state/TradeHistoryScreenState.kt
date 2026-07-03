package com.groww.papertrading.presentation.ui.state

import com.groww.papertrading.data.models.Trade

data class TradeHistoryScreenState(
    val trades: List<Trade> = emptyList(),
    val filteredTrades: List<Trade> = emptyList(),
    val selectedSymbol: String? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)
