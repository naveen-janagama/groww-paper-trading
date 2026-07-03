package com.groww.papertrading.data.api

import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface GrowwApiClient {

    @GET("api/v1/stock/quote")
    suspend fun getStockQuote(
        @Header("Authorization") authToken: String,
        @Query("symbol") symbol: String
    ): StockQuoteResponse

    @GET("api/v1/market/search")
    suspend fun searchStocks(
        @Header("Authorization") authToken: String,
        @Query("query") query: String,
        @Query("limit") limit: Int = 20
    ): SearchResponse

    @GET("api/v1/market/top-gainers")
    suspend fun getTopGainers(
        @Header("Authorization") authToken: String
    ): TopMoversResponse

    @GET("api/v1/market/top-losers")
    suspend fun getTopLosers(
        @Header("Authorization") authToken: String
    ): TopMoversResponse
}

data class StockQuoteResponse(
    val status: String,
    val data: StockQuoteData
)

data class StockQuoteData(
    val symbol: String,
    val name: String,
    val currentPrice: Double,
    val high: Double,
    val low: Double,
    val open: Double,
    val close: Double,
    val volume: Long,
    val percentChange: Double
)

data class SearchResponse(
    val status: String,
    val data: List<SearchResult>
)

data class SearchResult(
    val symbol: String,
    val name: String,
    val type: String
)

data class TopMoversResponse(
    val status: String,
    val data: List<StockQuoteData>
)
