package com.example.cryptogo.ui

import android.app.Activity
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.example.cryptogo.adapter.MarketAdapter
import com.example.cryptogo.api.ApiInterface
import com.example.cryptogo.api.ApiUtlis
import com.example.cryptogo.databinding.FragmentTopgainerBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

class TopgainerFragment : Fragment() {
    lateinit var binding: FragmentTopgainerBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTopgainerBinding.inflate(layoutInflater)
        // Inflate the layout for this fragment
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getResponce()
    }
    private fun getResponce() {

        binding.loading.visibility = View.VISIBLE
        lifecycleScope.launch(Dispatchers.IO) {
            val result = ApiUtlis.getInstance().create(ApiInterface::class.java).getMarketData()
            if(result.body()!=null) {
                withContext(Dispatchers.Main) {
                   var list = result.body()!!.data.cryptoCurrencyList

                    Collections.sort(list) {
                        i,j ->(j.quotes[0].percentChange24h.toInt())
                        .compareTo(i.quotes[0].percentChange24h.toInt())
                    }
                    (context as Activity).runOnUiThread{
                        binding.loading.visibility = View.INVISIBLE
                        binding.topGainRc.visibility = View.VISIBLE
                        binding.topGainRc.adapter = MarketAdapter(requireContext(), list,"home" )
                    }
                }
            }
        }
    }   // getResponse End
}