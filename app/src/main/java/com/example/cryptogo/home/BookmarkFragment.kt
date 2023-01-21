package com.example.cryptogo.home

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.cryptogo.adapter.MarketAdapter
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

        binding.loading.visibility = View.VISIBLE
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
            var adapter = MarketAdapter(requireContext(),bookmarkList,"bookmark")
            binding.loading.visibility = View.INVISIBLE
            binding.bookmarkRc.adapter = adapter
            swipeTodelete(adapter)
        }

    }

    private fun swipeTodelete(adapter: MarketAdapter) {
        // left swipe to delete the data from room db and recycler view
        var itemTouchHelperCallbacks = object : ItemTouchHelper.SimpleCallback(0,
            ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT
        ){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val pos = viewHolder.adapterPosition
                val coin = adapter.getCoin(pos)
                lifecycleScope.launch(Dispatchers.Main) {
                    viewmodel.deleteCoin(coin.coinNumber)

                }

                Toast.makeText(requireContext(),"deleted", Toast.LENGTH_SHORT).show()
            }

        }
        // attach swipe to recycler view
        ItemTouchHelper(itemTouchHelperCallbacks).apply {
            attachToRecyclerView(binding.bookmarkRc)
        }
    }
}