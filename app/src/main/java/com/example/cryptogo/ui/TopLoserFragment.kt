package com.example.cryptogo.ui

import android.app.Activity
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.example.cryptogo.adapter.TopGainerAdapter
import com.example.cryptogo.adapter.TopLoserAdapter
import com.example.cryptogo.api.ApiInterface
import com.example.cryptogo.api.ApiUtlis
import com.example.cryptogo.databinding.FragmentTopLoserBinding
import com.example.cryptogo.model.CryptoCurrency
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

class TopLoserFragment : Fragment() {

    private lateinit var binding: FragmentTopLoserBinding
    private lateinit var list: List<CryptoCurrency>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        println("mujeeeb")
        binding = FragmentTopLoserBinding.inflate(layoutInflater)
        // Inflate the layout for this fragment

        getResponse()


        return binding.root
    }


    private fun getResponse() {

        lifecycleScope.launch(Dispatchers.IO) {
            val result = ApiUtlis.getInstance().create(ApiInterface::class.java).getMarketData()
            if(result.body()!=null) {
                withContext(Dispatchers.Main) {
                    list = result.body()!!.data.cryptoCurrencyList

                    Collections.sort(list) {
                            i,j ->(i.quotes[0].percentChange30d.toInt())
                        .compareTo(j.quotes[0].percentChange30d.toInt())
                    }
                }
                (context as Activity).runOnUiThread{
                    binding.topLoserRc.adapter = TopGainerAdapter(requireContext(), list )
                }
            }
        }
    }


}