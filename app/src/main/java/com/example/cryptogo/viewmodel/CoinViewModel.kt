package com.example.cryptogo.viewmodel

import com.example.cryptogo.model.Coin
import com.example.cryptogo.repository.CoinRepostory

class CoinViewModel(
    private val repository: CoinRepostory
) {

    fun insertCoin(coin: Coin) = repository.insertCoin(coin)
//    suspend fun deleteCoin(coin: Coin) = repository.deleteCoin(coin)
    fun getAllCoin() = repository.getAllCoins()
}