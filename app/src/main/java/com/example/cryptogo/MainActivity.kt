package com.example.cryptogo

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.cryptogo.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // bottomnav bar
        init()

//        binding.searchEditText.setOnClickListener{
//            changeFragment()
//        }

    }

//    private fun changeFragment() {
//        val navController: NavController = findNavController(this, R.id.host)
//        navController.navigateUp()
//        navController.navigate(R.id.marketFragment)
////        binding.searchEditText.visibility = View.GONE
//    }

    fun init() {
            val navControl = findNavController(findViewById(R.id.host))
            binding.bottomNavBar.setupWithNavController(navControl)
        }
}