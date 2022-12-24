package com.example.cryptogo.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.example.cryptogo.adapter.TopCoinAdapter
import com.example.cryptogo.adapter.TopGainerAdapter
import com.example.cryptogo.api.ApiInterface
import com.example.cryptogo.api.ApiUtlis
import com.example.cryptogo.databinding.FragmentTopgainerBinding
import com.example.cryptogo.model.CryptoCurrency
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

class TopgainerFragment : Fragment() {
    lateinit var binding: FragmentTopgainerBinding
    lateinit var list: List<CryptoCurrency>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTopgainerBinding.inflate(layoutInflater)
        // Inflate the layout for this fragment
        getResponce()

        return binding.root
    }

    private fun getResponce() {

        lifecycleScope.launch(Dispatchers.IO) {
            val result = ApiUtlis.getInstance().create(ApiInterface::class.java).getMarketData()
            if(result.body()!=null) {
                withContext(Dispatchers.Main) {
                    list = result.body()!!.data.cryptoCurrencyList

                    Collections.sort(list) {
                        i,j ->(i.quotes[0].percentChange24h.toInt())
                        .compareTo(j.quotes[0].percentChange24h.toInt())
                    }
                }
            }

            try {
                binding.topGainRc.adapter = TopGainerAdapter(requireContext(), list )
            } catch (e: Throwable) {
                // handle exception
            }

        }
    }
}