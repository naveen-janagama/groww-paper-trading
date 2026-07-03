package com.groww.papertrading.data.repository

import com.groww.papertrading.data.database.TradeDao
import com.groww.papertrading.data.database.PortfolioDao
import com.groww.papertrading.data.database.HoldingDao
import com.groww.papertrading.data.models.Trade
import com.groww.papertrading.data.models.Holding
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TradeRepository @Inject constructor(
    private val tradeDao: TradeDao,
    private val portfolioDao: PortfolioDao,
    private val holdingDao: HoldingDao
) {

    fun getTradesFlow(userId: String): Flow<List<Trade>> =
        tradeDao.getTradesFlow(userId)

    fun getSymbolTradesFlow(userId: String, symbol: String): Flow<List<Trade>> =
        tradeDao.getSymbolTradesFlow(userId, symbol)

    suspend fun placeBuyOrder(
        userId: String,
        symbol: String,
        quantity: Int,
        price: Double
    ): Result<Trade> = try {
        val portfolio = portfolioDao.getPortfolio(userId)
            ?: return Result.failure(Exception("Portfolio not found"))

        val totalCost = quantity * price
        if (portfolio.availableFunds < totalCost) {
            return Result.failure(Exception("Insufficient funds"))
        }

        val trade = Trade(
            userId = userId,
            symbol = symbol,
            quantity = quantity,
            price = price,
            type = "BUY",
            totalAmount = totalCost
        )

        tradeDao.insertTrade(trade)

        val updatedPortfolio = portfolio.copy(
            totalInvested = portfolio.totalInvested + totalCost,
            availableFunds = portfolio.availableFunds - totalCost,
            lastUpdated = System.currentTimeMillis()
        )
        portfolioDao.updatePortfolio(updatedPortfolio)

        val existingHolding = holdingDao.getHolding(userId, symbol)
        if (existingHolding != null) {
            val newQuantity = existingHolding.quantity + quantity
            val newTotalCost = existingHolding.totalCost + totalCost
            val newAvgPrice = newTotalCost / newQuantity
            val updatedHolding = existingHolding.copy(
                quantity = newQuantity,
                avgBuyPrice = newAvgPrice,
                totalCost = newTotalCost,
                currentValue = newQuantity * price,
                unrealizedPnl = (newQuantity * price) - newTotalCost,
                unrealizedPnlPercentage = ((newQuantity * price) - newTotalCost) / newTotalCost * 100,
                lastUpdated = System.currentTimeMillis()
            )
            holdingDao.updateHolding(updatedHolding)
        } else {
            val holding = Holding(
                id = "${userId}_$symbol",
                userId = userId,
                symbol = symbol,
                quantity = quantity,
                avgBuyPrice = price,
                currentPrice = price,
                totalCost = totalCost,
                currentValue = totalCost
            )
            holdingDao.insertHolding(holding)
        }

        Result.success(trade)
    } catch (e: Exception) {
        Result.failure(e)
    }

    suspend fun placeSellOrder(
        userId: String,
        symbol: String,
        quantity: Int,
        price: Double
    ): Result<Trade> = try {
        val holding = holdingDao.getHolding(userId, symbol)
            ?: return Result.failure(Exception("No holding found"))

        if (holding.quantity < quantity) {
            return Result.failure(Exception("Insufficient quantity"))
        }

        val totalAmount = quantity * price
        val costOfSoldQuantity = (quantity * holding.avgBuyPrice)
        val pnl = totalAmount - costOfSoldQuantity

        val trade = Trade(
            userId = userId,
            symbol = symbol,
            quantity = quantity,
            price = price,
            type = "SELL",
            totalAmount = totalAmount,
            pnl = pnl
        )

        tradeDao.insertTrade(trade)

        val portfolio = portfolioDao.getPortfolio(userId)!!
        val updatedPortfolio = portfolio.copy(
            totalInvested = portfolio.totalInvested - costOfSoldQuantity,
            availableFunds = portfolio.availableFunds + totalAmount,
            totalPnl = portfolio.totalPnl + pnl,
            lastUpdated = System.currentTimeMillis()
        )
        portfolioDao.updatePortfolio(updatedPortfolio)

        val remainingQuantity = holding.quantity - quantity
        if (remainingQuantity == 0) {
            holdingDao.deleteHolding(holding)
        } else {
            val remainingCost = holding.totalCost - costOfSoldQuantity
            val updatedHolding = holding.copy(
                quantity = remainingQuantity,
                totalCost = remainingCost,
                currentValue = remainingQuantity * price,
                unrealizedPnl = (remainingQuantity * price) - remainingCost,
                unrealizedPnlPercentage = ((remainingQuantity * price) - remainingCost) / remainingCost * 100,
                lastUpdated = System.currentTimeMillis()
            )
            holdingDao.updateHolding(updatedHolding)
        }

        Result.success(trade)
    } catch (e: Exception) {
        Result.failure(e)
    }
}
