package com.groww.papertrading.data.database

import androidx.room.*
import com.groww.papertrading.data.models.Portfolio
import kotlinx.coroutines.flow.Flow

@Dao
interface PortfolioDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPortfolio(portfolio: Portfolio)

    @Query("SELECT * FROM portfolio WHERE userId = :userId")
    fun getPortfolioFlow(userId: String): Flow<Portfolio?>

    @Query("SELECT * FROM portfolio WHERE userId = :userId")
    suspend fun getPortfolio(userId: String): Portfolio?

    @Update
    suspend fun updatePortfolio(portfolio: Portfolio)

    @Delete
    suspend fun deletePortfolio(portfolio: Portfolio)
}
