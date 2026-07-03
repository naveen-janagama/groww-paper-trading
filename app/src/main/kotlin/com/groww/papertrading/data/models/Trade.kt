package com.groww.papertrading.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "trades")
data class Trade(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val userId: String,
    val symbol: String,
    val quantity: Int,
    val price: Double,
    val type: String,
    val totalAmount: Double,
    val timestamp: Long = System.currentTimeMillis(),
    val pnl: Double = 0.0,
    val status: String = "COMPLETED"
)
