package com.groww.papertrading.data.database

import androidx.room.*
import com.groww.papertrading.data.models.Holding
import kotlinx.coroutines.flow.Flow

@Dao
interface HoldingDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHolding(holding: Holding)

    @Query("SELECT * FROM holdings WHERE userId = :userId")
    fun getHoldingsFlow(userId: String): Flow<List<Holding>>

    @Query("SELECT * FROM holdings WHERE userId = :userId")
    suspend fun getHoldings(userId: String): List<Holding>

    @Query("SELECT * FROM holdings WHERE userId = :userId AND symbol = :symbol")
    suspend fun getHolding(userId: String, symbol: String): Holding?

    @Query("SELECT * FROM holdings WHERE userId = :userId AND symbol = :symbol")
    fun getHoldingFlow(userId: String, symbol: String): Flow<Holding?>

    @Update
    suspend fun updateHolding(holding: Holding)

    @Delete
    suspend fun deleteHolding(holding: Holding)
}
