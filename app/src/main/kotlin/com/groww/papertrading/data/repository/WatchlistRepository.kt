package com.groww.papertrading.data.repository

import com.groww.papertrading.data.database.WatchlistDao
import com.groww.papertrading.data.models.Watchlist
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class WatchlistRepository @Inject constructor(
    private val watchlistDao: WatchlistDao
) {

    fun getWatchlistFlow(userId: String): Flow<List<Watchlist>> =
        watchlistDao.getWatchlistFlow(userId)

    suspend fun addToWatchlist(
        userId: String,
        symbol: String,
        name: String,
        currentPrice: Double,
        percentChange: Double
    ) {
        val watchlist = Watchlist(
            id = "${userId}_$symbol",
            userId = userId,
            symbol = symbol,
            name = name,
            currentPrice = currentPrice,
            percentChange = percentChange
        )
        watchlistDao.addToWatchlist(watchlist)
    }

    suspend fun removeFromWatchlist(userId: String, symbol: String) {
        watchlistDao.removeFromWatchlistBySymbol(userId, symbol)
    }

    suspend fun isInWatchlist(userId: String, symbol: String): Boolean {
        return watchlistDao.getWatchlistItem(userId, symbol) != null
    }
}
