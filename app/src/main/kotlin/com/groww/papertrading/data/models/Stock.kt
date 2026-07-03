package com.groww.papertrading.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "stocks")
data class Stock(
    @PrimaryKey
    val symbol: String,
    val name: String,
    val currentPrice: Double,
    val high: Double,
    val low: Double,
    val open: Double,
    val close: Double,
    val volume: Long,
    val percentChange: Double,
    val timestamp: Long = System.currentTimeMillis()
)
