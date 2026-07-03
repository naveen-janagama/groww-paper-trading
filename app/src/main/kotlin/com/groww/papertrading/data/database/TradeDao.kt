package com.groww.papertrading.data.database

import androidx.room.*
import com.groww.papertrading.data.models.Trade
import kotlinx.coroutines.flow.Flow

@Dao
interface TradeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTrade(trade: Trade)

    @Query("SELECT * FROM trades WHERE userId = :userId ORDER BY timestamp DESC")
    fun getTradesFlow(userId: String): Flow<List<Trade>>

    @Query("SELECT * FROM trades WHERE userId = :userId ORDER BY timestamp DESC LIMIT :limit")
    suspend fun getRecentTrades(userId: String, limit: Int = 50): List<Trade>

    @Query("SELECT * FROM trades WHERE userId = :userId AND symbol = :symbol ORDER BY timestamp DESC")
    fun getSymbolTradesFlow(userId: String, symbol: String): Flow<List<Trade>>

    @Query("SELECT * FROM trades WHERE id = :tradeId")
    suspend fun getTrade(tradeId: Int): Trade?

    @Update
    suspend fun updateTrade(trade: Trade)

    @Delete
    suspend fun deleteTrade(trade: Trade)

    @Query("SELECT COUNT(*) FROM trades WHERE userId = :userId")
    suspend fun getTradeCount(userId: String): Int
}
