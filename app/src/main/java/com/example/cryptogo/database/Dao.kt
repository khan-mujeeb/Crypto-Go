package com.example.cryptogo.database

import androidx.lifecycle.LiveData
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.cryptogo.model.Coin

@androidx.room.Dao
interface Dao {

    @Query("SELECT * FROM coin_table")
    fun getAll(): List<Coin>

    @Insert
    fun insert(coin: Coin)

    @Query("DELETE FROM coin_table WHERE coinNumber = :coin_number")
    suspend fun deleteEntry(coin_number: Int)

}