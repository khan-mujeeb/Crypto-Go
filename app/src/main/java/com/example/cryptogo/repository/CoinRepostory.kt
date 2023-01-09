package com.example.cryptogo.repository

import androidx.lifecycle.LiveData
import com.example.cryptogo.database.CoinDatabase

import com.example.cryptogo.model.Coin

class CoinRepostory(private val coinDatabase: CoinDatabase) {
    fun getAllCoins(): LiveData<List<Coin>> = coinDatabase.getCoinDao().getAll()
    fun insertCoin(coin: Coin) = coinDatabase.getCoinDao().insert(coin)

//    suspend fun deleteCoin(coin: Coin) = coinDatabase.getCoinDao().deleteEntry(coin)
}