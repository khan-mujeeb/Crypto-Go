package com.example.cryptogo.home

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.cryptogo.MainActivity.Companion.list
import com.example.cryptogo.adapter.MarketAdapter
import com.example.cryptogo.api.ApiInterface
import com.example.cryptogo.api.ApiUtlis
import com.example.cryptogo.databinding.FragmentMarketBinding
import com.example.cryptogo.model.CryptoCurrency
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MarketFragment : Fragment() {

    private lateinit var adapter: MarketAdapter
    private lateinit var binding: FragmentMarketBinding
    private lateinit var searchText: String


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMarketBinding.inflate(layoutInflater)

        // making search edit text invisible


        getResponce()
        searchCoin()


        // Inflate the layout for this fragment
        return binding.root
    }

    private fun getResponce() {
        binding.loading.visibility = View.VISIBLE

        adapter = MarketAdapter(requireContext(), list)
        binding.marketRc.adapter = adapter

        lifecycleScope.launch(Dispatchers.IO) {
            val result = ApiUtlis.getInstance().create(ApiInterface::class.java).getMarketData()
            if (result.body() != null) {
                withContext(Dispatchers.Main) {
                    list = result.body()!!.data.cryptoCurrencyList
                    adapter.updateDataList(list)
                    binding.loading.visibility = View.INVISIBLE
                    binding.marketRc.visibility = View.VISIBLE
                }
            }
        }
    }

    fun searchCoin() {

        binding.searchEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {
                searchText = p0.toString().toLowerCase()
                updateRecyclerView()
            }

        })

    }

    private fun updateRecyclerView() {
        var data = ArrayList<CryptoCurrency>()
        for (item in list) {
            val coinName = item.name.toLowerCase()
            val coinSymbol = item.symbol.toLowerCase()

            if (coinName.contains(searchText) || coinSymbol.contains(searchText)) {
                data.add(item)
            }
        }
        adapter.updateDataList(data)
    }


}