package com.example.cryptogo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.cryptogo.R
import com.example.cryptogo.adapter.ViewPagerAdapter
import com.example.cryptogo.databinding.ActivityMainBinding
import com.example.cryptogo.ui.TopLoserFragment
import com.example.cryptogo.ui.TopgainerFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // bottomnav bar
        init()


    }
    fun init() {
        val navControl = findNavController(findViewById(R.id.host))
        binding.bottomNavBar.setupWithNavController(navControl)
    }
}