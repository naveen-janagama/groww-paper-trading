package com.groww.papertrading.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User(
    @PrimaryKey
    val userId: String,
    val email: String,
    val name: String,
    val demoFunds: Double = 100000.0,
    val createdAt: Long = System.currentTimeMillis(),
    val isActive: Boolean = true
)
