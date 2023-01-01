package com.example.cryptogo.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cryptogo.R
import com.example.cryptogo.databinding.CoinItemviewBinding
import com.example.cryptogo.model.CryptoCurrency

class TopLoserAdapter(var context: Context, var list: List<CryptoCurrency>): RecyclerView.Adapter<TopLoserAdapter.TopLoserViewHolder>() {
    class TopLoserViewHolder(view: View): RecyclerView.ViewHolder(view){
        val binding = CoinItemviewBinding.bind(view)
        val name = binding.fullName
        val shortName = binding.shortName
        val price = binding.price
        val coinImage = binding.coinImg
        val graph = binding.graph
        val deviation = binding.deviation

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopLoserViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(
                R.layout.coin_itemview,
                parent,
                false
            )
        return TopLoserViewHolder(view)
    }

    override fun onBindViewHolder(holder: TopLoserViewHolder, position: Int) {
        holder.name.text = list[position].name
        Glide.with(context)
            .load("https://s2.coinmarketcap.com/static/img/coins/64x64/${list[position].id}.png")
            .thumbnail(Glide.with(context).load(R.drawable.spinner))
            .into(holder.coinImage)

        Glide.with(context)
            .load("https://s3.coinmarketcap.com/generated/sparklines/web/7d/usd/${list[position].id}.png")
            .thumbnail(Glide.with(context).load(R.drawable.spinner))
            .into(holder.graph)

        holder.shortName.text = list[position].symbol
        val p = list[position].quotes[0].price
        holder.price.text = "$${String.format("%.7f",p)}"


        val change = list[position].quotes[0].percentChange24h
        if (change > 0) {
            holder.deviation.setTextColor(context.resources.getColor(R.color.green))
            holder.deviation.text = "+${String.format("%.2f",change)}%"
        } else {
            holder.deviation.setTextColor(context.resources.getColor(R.color.red))
            holder.deviation.text = "${String.format("%.2f",change)}%"
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

}