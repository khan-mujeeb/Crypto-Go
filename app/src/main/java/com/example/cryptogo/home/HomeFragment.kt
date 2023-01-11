package com.example.cryptogo.home

import android.app.Activity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
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
import kotlinx.coroutines.withContext

class HomeFragment : Fragment() {
//    lateinit var list: List<CryptoCurrency>
    private lateinit var binding: FragmentHomeBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // making search edit text visible
        val search = activity?.findViewById<EditText>(R.id.search_edit_text)
        search?.visibility = View.VISIBLE

        binding = FragmentHomeBinding.inflate(layoutInflater)
        // Inflate the layout for this fragment
        getResponce()
        switchFragment()


        return binding.root
    }


    private fun getResponce() {
//        binding.loading.visibility = View.VISIBLE

        lifecycleScope.launch(Dispatchers.IO) {
            val result = ApiUtlis.getInstance().create(ApiInterface::class.java).getMarketData()
            var list = result.body()!!.data.cryptoCurrencyList

            withContext(Dispatchers.IO) {
                // run on main thread
                (context as Activity).runOnUiThread {
                    binding.loading.visibility = View.INVISIBLE
                    binding.topCoinsRc.visibility = View.VISIBLE
                    binding.topCoinsRc.adapter = TopCoinAdapter(requireContext(), list)
//
                }

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