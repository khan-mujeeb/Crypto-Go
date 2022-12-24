package com.example.cryptogo.home

import android.app.Activity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import com.example.cryptogo.R
import com.example.cryptogo.adapter.TopCoinAdapter
import com.example.cryptogo.adapter.ViewPagerAdapter
import com.example.cryptogo.api.ApiInterface
import com.example.cryptogo.api.ApiUtlis
import com.example.cryptogo.databinding.FragmentHomeBinding
import com.example.cryptogo.model.CryptoCurrency
import com.example.cryptogo.ui.TopLoserFragment
import com.example.cryptogo.ui.TopgainerFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {
    lateinit var list: List<CryptoCurrency>
    private lateinit var binding: FragmentHomeBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        // Inflate the layout for this fragment
        switchFragment()
        getResponce()



        return binding.root
    }



    private fun getResponce() {
//        activity?.runOnUiThread(Runnable {
//        })
        lifecycleScope.launch(Dispatchers.IO) {
            val result = ApiUtlis.getInstance().create(ApiInterface::class.java).getMarketData()
            list = result.body()!!.data.cryptoCurrencyList
            println("mujeeb $list")
            try {
                binding.topCoinsRc.adapter = TopCoinAdapter(requireContext(), list)
            } catch (e: Throwable) {
                // handle exception
            }

        }
    }

    fun switchFragment() {
        val fragmentList = ArrayList<Fragment>()
        fragmentList.add(TopgainerFragment())
        fragmentList.add(TopLoserFragment())

        val fragmentManager = (activity as FragmentActivity).supportFragmentManager
        val adapter = ViewPagerAdapter(requireContext(), fragmentManager, fragmentList)
        binding.viewPager.adapter = adapter
        binding.tabs.setupWithViewPager(binding.viewPager)
    }


}