package com.example.cryptogo.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "coin_table")
data class Coin(
    @PrimaryKey(autoGenerate = true)
    val id: Int?,
    val coinNumber: Int
)
