package com.example.cryptogo.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import com.example.cryptogo.R

class MarketFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // making search edit text visible
        val search = activity?.findViewById<EditText>(R.id.search_edit_text)
        search?.visibility = View.VISIBLE

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_market, container, false)
    }

}