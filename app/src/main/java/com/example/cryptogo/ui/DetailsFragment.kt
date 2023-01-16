package com.example.cryptogo.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.cryptogo.activity.MainActivity.Companion.viewmodel
import com.example.cryptogo.R
import com.example.cryptogo.databinding.FragmentDetailsBinding
import com.example.cryptogo.model.Coin
import com.example.cryptogo.model.CryptoCurrency
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailsFragment : Fragment() {


    private lateinit var binding: FragmentDetailsBinding
    private val args: DetailsFragmentArgs by navArgs()
//    private lateinit var bookMarkedCoinList: List<Coin>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val search = activity?.findViewById<EditText>(R.id.search_edit_text)
        search?.visibility = View.GONE
        binding = FragmentDetailsBinding.inflate(layoutInflater)

        val data: CryptoCurrency = args.data!!

        binding.addWatchlistButton.setOnClickListener {
            val coin = Coin(
                null,
                data.cmcRank
            )
            lifecycleScope.launch(Dispatchers.IO) {
                viewmodel.insertCoin(coin)
            }
            binding.addWatchlistButton.setImageResource(R.drawable.ic_star)
            Toast.makeText(requireContext(), "saved", Toast.LENGTH_LONG).show()
        }

        //set data
        setData(data)

        // set chart
        setChart(data)

        //btn
        setBtnOnClick(data)



        return binding.root
    }

    private fun setBtnOnClick(data: CryptoCurrency) {

        val _15m = binding.btn15m
        val _1hr = binding.btn1hr
        val _4hr = binding.btn4hr
        val _1d = binding.btn1D
        val _1W = binding.btn1W
        val _1M = binding.btn1M

        val clickListener = View.OnClickListener {

            when (it.id) {
                _15m.id -> loadChartData(it, "15", data, _1W, _1M, _1d, _1hr, _4hr)
                _1hr.id -> loadChartData(it, "1H", data, _1W, _1M, _1d, _15m, _4hr)
                _4hr.id -> loadChartData(it, "4H", data, _1W, _1M, _1d, _1hr, _15m)
                _1d.id -> loadChartData(it, "D", data, _1W, _1M, _15m, _1hr, _4hr)
                _1W.id -> loadChartData(it, "W", data, _15m, _1M, _1d, _1hr, _4hr)
                _1M.id -> loadChartData(it, "M", data, _1W, _15m, _1d, _1hr, _4hr)

            }
        }

        // setting initial background color
        _15m.setBackgroundColor(R.color.olive_green!!)
        disableBtn(_1M, _1d, _1hr, _4hr, _1W)

        _15m.setOnClickListener(clickListener)
        _1hr.setOnClickListener(clickListener)
        _4hr.setOnClickListener(clickListener)
        _1d.setOnClickListener(clickListener)
        _1W.setOnClickListener(clickListener)
        _1M.setOnClickListener(clickListener)

    }

    private fun loadChartData(
        it: View?,
        s: String,
        data: CryptoCurrency,
        _1W: AppCompatButton,
        _1M: AppCompatButton,
        _1d: AppCompatButton,
        _1hr: AppCompatButton,
        _4hr: AppCompatButton
    ) {
        it!!.setBackgroundColor(R.color.olive_green!!)
        disableBtn(_1M, _1d, _1hr, _4hr, _1W)

        val graphView = binding.graph
        graphView.settings.javaScriptEnabled = true
        graphView.setLayerType(View.LAYER_TYPE_SOFTWARE, null)

        var url =
            "https://s.tradingview.com/widgetembed/?frameElementId=tradingview_76d87&symbol=${data.symbol}USD&interval=${s}&hidesidetoolbar=1&hidetoptoolbar=1&symboledit=1&saveimage=1&toolbarbg=F1F3F6&studies=[]&hideideas=1&theme=Dark&style=1&timezone=Etc%2FUTC&studies_overrides={}&overrides={}&enabled_features=[]&disabled_features=[]&locale=en&utm_source=coinmarketcap.com&utm_medium=widget&utm_campaign=chart&utm_term=BTCUSDT"
        binding.graph.loadUrl(url)
    }

    private fun disableBtn(
        _1M: AppCompatButton,
        _1d: AppCompatButton,
        _1hr: AppCompatButton,
        _4hr: AppCompatButton,
        _1W: AppCompatButton
    ) {
        _1W.background = null
        _1M.background = null
        _1d.background = null
        _4hr.background = null
        _1hr.background = null
    }

    private fun setData(data: CryptoCurrency) {
        // set star symbol
        setBookMarkIcon(data.cmcRank)

        // symbol
        binding.detailSymbolTextView.text = data.symbol

        // price
        val price = data.quotes[0].price
        binding.detailPriceTextView.text = "$${String.format("%.8f", price)}"

        // logo
        setLogo(data)

        //change
        percentChange(data)

        coinDetails(data)
    }

    private fun coinDetails(data: CryptoCurrency) {
        binding.name.text = data.name
        binding.rank.text = data.cmcRank.toString()
        binding.marketCap.text = (data.quotes[0].marketCap).toInt().toString()
        binding.totalSupply.text = data.totalSupply.toString()
        binding.maxSupply.text = data.maxSupply.toString()
    }

    private fun percentChange(data: CryptoCurrency) {
        val change = data.quotes[0].percentChange24h
        val holder = binding.detailChangeTextView
        val ImgHolder = binding.detailChangeImageView
        if (change > 0) {
            holder.setTextColor(context?.resources!!.getColor(R.color.green))
            binding.detailChangeTextView.text = "${String.format("%.2f", change)}%"
            ImgHolder.setImageResource(R.drawable.ic_caret_up)
        } else {
            holder.setTextColor(context?.resources!!.getColor(R.color.red))
            holder.text = "${String.format("%.2f", change)}%"
            ImgHolder.setImageResource(R.drawable.ic_caret_down)
        }
    }

    private fun setLogo(data: CryptoCurrency) {
        Glide.with(this)
            .load("https://s2.coinmarketcap.com/static/img/coins/64x64/${data.id}.png")
            .thumbnail(Glide.with(this).load(R.drawable.spinner))
            .into(binding.detailImageView)
    }

    private fun setBookMarkIcon(cmcRank: Int) {
//        getResponce()
//        for (item in bookMarkedCoinList) {
//            if (item.coinNumber == cmcRank) {
//                binding.addWatchlistButton.setImageResource(R.drawable.ic_star)
//            }
//        }
    }


    private fun setChart(data: CryptoCurrency) {
        val graphView = binding.graph
        graphView.settings.javaScriptEnabled = true
        graphView.setLayerType(View.LAYER_TYPE_SOFTWARE, null)
        val s = "15"


        var url =
            "https://s.tradingview.com/widgetembed/?frameElementId=tradingview_76d87&symbol=${data.symbol}USD&interval=${s}&hidesidetoolbar=1&hidetoptoolbar=1&symboledit=1&saveimage=1&toolbarbg=F1F3F6&studies=[]&hideideas=1&theme=Dark&style=1&timezone=Etc%2FUTC&studies_overrides={}&overrides={}&enabled_features=[]&disabled_features=[]&locale=en&utm_source=coinmarketcap.com&utm_medium=widget&utm_campaign=chart&utm_term=BTCUSDT"
        binding.graph.loadUrl(url)
    }

//    private fun getResponce() {
//        lifecycleScope.launch(Dispatchers.IO) {
//            bookMarkedCoinList = viewmodel.getAllCoin()
//        }
//    }

}