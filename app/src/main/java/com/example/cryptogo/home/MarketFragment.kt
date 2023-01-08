package com.example.cryptogo.home

import android.app.Activity
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.lifecycle.lifecycleScope
import com.example.cryptogo.R
import com.example.cryptogo.adapter.MarketAdapter
import com.example.cryptogo.adapter.TopGainerAdapter
import com.example.cryptogo.api.ApiInterface
import com.example.cryptogo.api.ApiUtlis
import com.example.cryptogo.databinding.FragmentMarketBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

class MarketFragment : Fragment() {

    private lateinit var binding: FragmentMarketBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMarketBinding.inflate(layoutInflater)
        // making search edit text visible
        val search = activity?.findViewById<EditText>(R.id.search_edit_text)
        search?.visibility = View.VISIBLE

        getResponce()
        // Inflate the layout for this fragment
        return binding.root
    }

    private fun getResponce() {
        binding.loading.visibility = View.VISIBLE
        lifecycleScope.launch(Dispatchers.IO) {
            val result = ApiUtlis.getInstance().create(ApiInterface::class.java).getMarketData()
            if(result.body()!=null) {
                withContext(Dispatchers.Main) {
                    var list = result.body()!!.data.cryptoCurrencyList
                    binding.loading.visibility = View.INVISIBLE
                    binding.marketRc.visibility = View.VISIBLE
                    binding.marketRc.adapter = MarketAdapter(requireContext(),list)
                }
            }
        }
    }
}