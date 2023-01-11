package com.example.cryptogo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.navigation.Navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.cryptogo.database.CoinDatabase
import com.example.cryptogo.databinding.ActivityMainBinding
import com.example.cryptogo.model.CryptoCurrency
import com.example.cryptogo.repository.CoinRepostory
import com.example.cryptogo.viewmodel.CoinViewModel
import com.example.cryptogo.viewmodel.CoinViewModelFactory


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // bottomnav bar
        init()

        coinDatabase = CoinDatabase(this)
        repository = CoinRepostory(coinDatabase)
        factory = CoinViewModelFactory(repository)
        viewmodel = CoinViewModel(repository)


    }

    fun init() {
        val navControl = findNavController(findViewById(R.id.host))
        binding.bottomNavBar.setupWithNavController(navControl)
    }

    companion object {
        var list = listOf<CryptoCurrency>()
        lateinit var viewmodel: CoinViewModel
        private lateinit var coinDatabase: CoinDatabase
        private lateinit var repository: CoinRepostory
        private lateinit var factory: CoinViewModelFactory
    }

}