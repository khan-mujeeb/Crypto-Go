package com.example.cryptogo.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.cryptogo.R
import com.example.cryptogo.utils.ConnectionLiveData

class Discconected : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_discconected)
        var cld = ConnectionLiveData(application)
        isConnected(cld)

    }
    private fun isConnected(cld: ConnectionLiveData) {
        cld.observe(this) { isConnected ->

            if (isConnected) {
                startActivity(Intent(this, MainActivity::class.java))
            }
        }
    }
}