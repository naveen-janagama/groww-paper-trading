package com.groww.papertrading.data.database

import androidx.room.*
import com.groww.papertrading.data.models.Watchlist
import kotlinx.coroutines.flow.Flow

@Dao
interface WatchlistDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addToWatchlist(watchlist: Watchlist)

    @Query("SELECT * FROM watchlist WHERE userId = :userId")
    fun getWatchlistFlow(userId: String): Flow<List<Watchlist>>

    @Query("SELECT * FROM watchlist WHERE userId = :userId")
    suspend fun getWatchlist(userId: String): List<Watchlist>

    @Query("SELECT * FROM watchlist WHERE userId = :userId AND symbol = :symbol")
    suspend fun getWatchlistItem(userId: String, symbol: String): Watchlist?

    @Delete
    suspend fun removeFromWatchlist(watchlist: Watchlist)

    @Query("DELETE FROM watchlist WHERE userId = :userId AND symbol = :symbol")
    suspend fun removeFromWatchlistBySymbol(userId: String, symbol: String)
}
