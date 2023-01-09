package com.example.cryptogo.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.cryptogo.repository.CoinRepostory

class CoinViewModelFactory( private val repository: CoinRepostory) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CoinViewModel(repository) as T
    }

}