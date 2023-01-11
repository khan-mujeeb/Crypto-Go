package com.example.cryptogo.home

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.cryptogo.MainActivity
import com.example.cryptogo.MainActivity.Companion.list
import com.example.cryptogo.adapter.MarketAdapter
import com.example.cryptogo.adapter.TopGainerAdapter
import com.example.cryptogo.api.ApiInterface
import com.example.cryptogo.api.ApiUtlis
import com.example.cryptogo.database.CoinDatabase
import com.example.cryptogo.databinding.FragmentBookmarkBinding
import com.example.cryptogo.model.Coin
import com.example.cryptogo.model.CryptoCurrency
import com.example.cryptogo.repository.CoinRepostory
import com.example.cryptogo.viewmodel.CoinViewModel
import com.example.cryptogo.viewmodel.CoinViewModelFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class BookmarkFragment : Fragment() {

    private lateinit var binding: FragmentBookmarkBinding
    lateinit var viewmodel: CoinViewModel
    lateinit var coinDatabase: CoinDatabase
    lateinit var repository: CoinRepostory
    lateinit var factory: CoinViewModelFactory
    private var bookmarkList = ArrayList<CryptoCurrency>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBookmarkBinding.inflate(layoutInflater)
        coinDatabase = CoinDatabase(requireContext())
        repository = CoinRepostory(coinDatabase)
        factory = CoinViewModelFactory(repository)
        viewmodel = CoinViewModel(repository)


        getResponce()


        return binding.root
    }

    private fun getResponce() {


        lifecycleScope.launch(Dispatchers.IO) {
            var dataList = viewmodel.getAllCoin()
            val result = ApiUtlis.getInstance().create(ApiInterface::class.java).getMarketData()
            if (result.body() != null) {
                withContext(Dispatchers.Main) {
                    val list = result.body()!!.data.cryptoCurrencyList
                    showList(list,dataList)
                }
            }
        }
    }

    private fun showList(list: List<CryptoCurrency>, dataList: List<Coin>) {
        for (i in dataList.indices) {
            for (item in list) {
                if (item.cmcRank == dataList[i].coinNumber) {
                    bookmarkList.add(item)
                }
            }
        }
        (context as Activity).runOnUiThread {

            var adapter = TopGainerAdapter(requireContext(), bookmarkList)
            binding.bookmarkRc.adapter = adapter
        }

    }


}