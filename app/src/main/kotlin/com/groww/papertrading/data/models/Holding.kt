package com.groww.papertrading.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "holdings")
data class Holding(
    @PrimaryKey
    val id: String,
    val userId: String,
    val symbol: String,
    val quantity: Int,
    val avgBuyPrice: Double,
    val currentPrice: Double,
    val totalCost: Double,
    val currentValue: Double,
    val unrealizedPnl: Double = 0.0,
    val unrealizedPnlPercentage: Double = 0.0,
    val lastUpdated: Long = System.currentTimeMillis()
)
