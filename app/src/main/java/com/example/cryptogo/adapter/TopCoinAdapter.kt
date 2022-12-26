package com.example.cryptogo.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cryptogo.R
import com.example.cryptogo.model.CryptoCurrency

class TopCoinAdapter(val context: Context, var list: List<CryptoCurrency>): RecyclerView.Adapter<TopCoinAdapter.TopCoinViewHolder>() {

    inner class TopCoinViewHolder(view: View): RecyclerView.ViewHolder(view) {
        var coinImage: ImageView = view.findViewById(R.id.coin_image)
        var coinName:  TextView = view.findViewById(R.id.name)
        var coinPrice: TextView = view.findViewById(R.id.coinPrice)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopCoinViewHolder {
        var view = LayoutInflater.from(parent.context)
            .inflate(R.layout.top_coin_itemview, parent, false)
        return TopCoinViewHolder(view)
    }

    override fun onBindViewHolder(holder: TopCoinViewHolder, position: Int) {
        holder.coinName.text = list[position].name

        Glide.with(context)
            .load("https://s2.coinmarketcap.com/static/img/coins/64x64/${list[position].id}.png")
            .thumbnail(Glide.with(context).load(R.drawable.spinner))
            .into(holder.coinImage)

        val change = list[position].quotes[0].percentChange24h
        if (change > 0) {
            holder.coinPrice.setTextColor(context.resources.getColor(R.color.green))
            holder.coinPrice.text = "+${String.format("%.2f",change)}%"
        } else {
            holder.coinPrice.setTextColor(context.resources.getColor(R.color.red))
            holder.coinPrice.text = "${String.format("%.2f",change)}%"
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}