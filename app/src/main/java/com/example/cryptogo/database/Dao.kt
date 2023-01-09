package com.example.cryptogo.database

import androidx.lifecycle.LiveData
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.cryptogo.model.Coin

@androidx.room.Dao
interface Dao {

    @Query("SELECT * FROM coin_table")
    fun getAll(): LiveData<List<Coin>>

    @Insert
    fun insert(coin: Coin)

//    @Delete
//    suspend fun deleteEntry(coin: Coin)
}