package com.example.cryptogo.model

import java.io.Serializable

data class Quote(
    val dominance: Double,
    val fullyDilluttedMarketCap: Double,
    val lastUpdated: String,
    val marketCap: Double,
    val marketCapByTotalSupply: Double,
    val name: String,
    val percentChange1h: Double,
    val percentChange24h: Double,
    val percentChange30d: Double,
    val percentChange60d: Double,
    val percentChange7d: Double,
    val percentChange90d: Double,
    val price: Double,
    val turnover: Double,
    val tvl: Double,
    val volume24h: Double,
    val ytdPriceChangePercentage: Double
): Serializable {
    override fun equals(other: Any?): Boolean {
        return super.equals(other)
    }

    override fun hashCode(): Int {
        return super.hashCode()
    }

    override fun toString(): String {
        return super.toString()
    }
}