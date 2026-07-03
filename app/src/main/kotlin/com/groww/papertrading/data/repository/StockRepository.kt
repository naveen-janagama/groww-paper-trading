package com.groww.papertrading.data.repository

import com.groww.papertrading.data.api.GrowwApiClient
import com.groww.papertrading.data.database.StockDao
import com.groww.papertrading.data.models.Stock
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class StockRepository @Inject constructor(
    private val apiClient: GrowwApiClient,
    private val stockDao: StockDao
) {

    fun getTopGainers(limit: Int = 10): Flow<List<Stock>> =
        stockDao.getTopGainersFlow(limit)

    fun getTopLosers(limit: Int = 10): Flow<List<Stock>> =
        stockDao.getTopLosersFlow(limit)

    fun getStock(symbol: String): Flow<Stock?> =
        stockDao.getStockFlow(symbol)

    suspend fun searchStocks(query: String, limit: Int = 20): List<Stock> =
        stockDao.searchStocks(query, limit)

    suspend fun refreshStockQuote(authToken: String, symbol: String) {
        try {
            val response = apiClient.getStockQuote(authToken, symbol)
            if (response.status == "success") {
                val stock = Stock(
                    symbol = response.data.symbol,
                    name = response.data.name,
                    currentPrice = response.data.currentPrice,
                    high = response.data.high,
                    low = response.data.low,
                    open = response.data.open,
                    close = response.data.close,
                    volume = response.data.volume,
                    percentChange = response.data.percentChange
                )
                stockDao.insertStock(stock)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    suspend fun refreshTopGainers(authToken: String) {
        try {
            val response = apiClient.getTopGainers(authToken)
            if (response.status == "success") {
                val stocks = response.data.map { data ->
                    Stock(
                        symbol = data.symbol,
                        name = data.name,
                        currentPrice = data.currentPrice,
                        high = data.high,
                        low = data.low,
                        open = data.open,
                        close = data.close,
                        volume = data.volume,
                        percentChange = data.percentChange
                    )
                }
                stockDao.insertStocks(stocks)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
