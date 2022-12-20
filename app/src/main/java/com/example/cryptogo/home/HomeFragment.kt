package com.example.cryptogo.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import com.example.cryptogo.R
import com.example.cryptogo.adapter.ViewPagerAdapter
import com.example.cryptogo.databinding.FragmentHomeBinding
import com.example.cryptogo.ui.TopLoserFragment
import com.example.cryptogo.ui.TopgainerFragment

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        // Inflate the layout for this fragment
        switchFragment()
        return binding.root
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