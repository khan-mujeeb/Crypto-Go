package com.example.cryptogo.viewmodel

import com.example.cryptogo.model.Coin
import com.example.cryptogo.repository.CoinRepostory

class CoinViewModel(
    private val repository: CoinRepostory
) {


    fun insertCoin(coin: Coin) = repository.insertCoin(coin)
    suspend fun deleteCoin(coin_number: Int) = repository.deleteCoin(coin_number)
    fun getAllCoin() = repository.getAllCoins()
}