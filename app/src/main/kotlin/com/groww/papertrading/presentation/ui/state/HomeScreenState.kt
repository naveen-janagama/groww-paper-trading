package com.groww.papertrading.presentation.ui.state

import com.groww.papertrading.data.models.Stock

data class HomeScreenState(
    val topGainers: List<Stock> = emptyList(),
    val topLosers: List<Stock> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)
