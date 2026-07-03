package com.groww.papertrading.presentation.ui.state

import com.groww.papertrading.data.models.Stock

data class SearchScreenState(
    val searchQuery: String = "",
    val searchResults: List<Stock> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)
