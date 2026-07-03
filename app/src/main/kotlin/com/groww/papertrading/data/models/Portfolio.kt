package com.groww.papertrading.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "portfolio")
data class Portfolio(
    @PrimaryKey
    val userId: String,
    val totalInvested: Double = 0.0,
    val currentValue: Double = 0.0,
    val availableFunds: Double = 100000.0,
    val totalPnl: Double = 0.0,
    val pnlPercentage: Double = 0.0,
    val lastUpdated: Long = System.currentTimeMillis()
)
