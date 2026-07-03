package com.groww.papertrading.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.groww.papertrading.data.repository.StockRepository
import com.groww.papertrading.presentation.ui.state.SearchScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val stockRepository: StockRepository
) : ViewModel() {

    private val _state = MutableStateFlow(SearchScreenState())
    val state: StateFlow<SearchScreenState> = _state.asStateFlow()

    fun searchStocks(query: String) {
        if (query.isEmpty()) {
            _state.update { it.copy(searchResults = emptyList()) }
            return
        }

        _state.update { it.copy(searchQuery = query, isLoading = true) }
        
        viewModelScope.launch {
            try {
                val results = stockRepository.searchStocks(query)
                _state.update { it.copy(searchResults = results, isLoading = false) }
            } catch (e: Exception) {
                _state.update { it.copy(error = e.message, isLoading = false) }
            }
        }
    }

    fun clearSearch() {
        _state.update { SearchScreenState() }
    }
}
