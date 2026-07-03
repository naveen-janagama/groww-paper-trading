package com.groww.papertrading.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "watchlist")
data class Watchlist(
    @PrimaryKey
    val id: String,
    val userId: String,
    val symbol: String,
    val name: String,
    val currentPrice: Double,
    val percentChange: Double,
    val addedAt: Long = System.currentTimeMillis()
)
