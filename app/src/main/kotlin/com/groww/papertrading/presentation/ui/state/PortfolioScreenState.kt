package com.groww.papertrading.presentation.ui.state

import com.groww.papertrading.data.models.Portfolio

data class PortfolioScreenState(
    val portfolio: Portfolio? = null,
    val totalValue: Double = 0.0,
    val dayChange: Double = 0.0,
    val isLoading: Boolean = false,
    val error: String? = null
)
