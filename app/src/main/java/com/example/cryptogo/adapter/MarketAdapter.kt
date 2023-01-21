package com.example.cryptogo.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cryptogo.R
import com.example.cryptogo.databinding.CoinItemviewBinding
import com.example.cryptogo.home.BookmarkFragment
import com.example.cryptogo.home.BookmarkFragmentDirections
import com.example.cryptogo.home.HomeFragmentDirections
import com.example.cryptogo.home.MarketFragmentDirections
import com.example.cryptogo.model.Coin
import com.example.cryptogo.model.CryptoCurrency

class MarketAdapter(var context: Context, var list: List<CryptoCurrency>, val From: String): RecyclerView.Adapter<MarketAdapter.TopLoserViewHolder>() {
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
        setCoinImage(holder.coinImage, position)
        setGraphImage(holder.graph, position)
        setChange(holder, position)
        holder.shortName.text = list[position].symbol
        val p = list[position].quotes[0].price
        holder.price.text = "$${String.format("%.7f",p)}"

        holder.itemView.setOnClickListener {
            when(From) {
                "home" -> {
                    Navigation.findNavController(it).navigate(
                        HomeFragmentDirections.actionHomeFragmentToDetailsFragment(list[position])
                    )
                }
                "market" -> {
                    Navigation.findNavController(it).navigate(
                        MarketFragmentDirections.actionMarketFragmentToDetailsFragment(list[position])
                    )
                }
                "bookmark" -> {
                    Navigation.findNavController(it).navigate(
                        BookmarkFragmentDirections.actionBookmarkFragmentToDetailsFragment(list[position])
                    )
                }
            }

        }

    }

    private fun setChange(holder: TopLoserViewHolder, position: Int) {
        val change = list[position].quotes[0].percentChange24h
        if (change > 0) {
            holder.deviation.setTextColor(context.resources.getColor(R.color.green))
            holder.deviation.text = "+${String.format("%.2f",change)}%"
        } else {
            holder.deviation.setTextColor(context.resources.getColor(R.color.red))
            holder.deviation.text = "${String.format("%.2f",change)}%"
        }
    }

    private fun setGraphImage(graph: ImageView, position: Int) {
        Glide.with(context)
            .load("https://s3.coinmarketcap.com/generated/sparklines/web/7d/usd/${list[position].id}.png")
            .thumbnail(Glide.with(context).load(R.drawable.spinner))
            .into(graph)
    }

    private fun setCoinImage(coinImage: ImageView, position: Int) {
        Glide.with(context)
            .load("https://s2.coinmarketcap.com/static/img/coins/64x64/${list[position].id}.png")
            .thumbnail(Glide.with(context).load(R.drawable.spinner))
            .into(coinImage)
    }

    fun updateDataList(datalist:List<CryptoCurrency>) {
        list = datalist
        notifyDataSetChanged()
    }

    fun getCoin(pos: Int): Coin {
        return Coin(null, list[pos].cmcRank)
    }

    override fun getItemCount(): Int {
        return list.size
    }

}