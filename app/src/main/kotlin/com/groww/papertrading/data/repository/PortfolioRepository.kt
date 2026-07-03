package com.groww.papertrading.data.repository

import com.groww.papertrading.data.database.PortfolioDao
import com.groww.papertrading.data.database.HoldingDao
import com.groww.papertrading.data.models.Portfolio
import com.groww.papertrading.data.models.Holding
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PortfolioRepository @Inject constructor(
    private val portfolioDao: PortfolioDao,
    private val holdingDao: HoldingDao
) {

    fun getPortfolioFlow(userId: String): Flow<Portfolio?> =
        portfolioDao.getPortfolioFlow(userId)

    fun getHoldingsFlow(userId: String): Flow<List<Holding>> =
        holdingDao.getHoldingsFlow(userId)

    suspend fun createPortfolio(userId: String): Portfolio {
        val portfolio = Portfolio(userId = userId)
        portfolioDao.insertPortfolio(portfolio)
        return portfolio
    }

    suspend fun updatePortfolioValue(userId: String, holdings: List<Holding>) {
        val portfolio = portfolioDao.getPortfolio(userId) ?: return

        val currentValue = holdings.sumOf { it.currentValue }
        val totalCost = holdings.sumOf { it.totalCost }
        val unrealizedPnl = currentValue - totalCost
        val totalPnl = portfolio.totalPnl + unrealizedPnl
        val pnlPercentage = if (totalCost > 0) (totalPnl / totalCost) * 100 else 0.0

        val updatedPortfolio = portfolio.copy(
            currentValue = currentValue,
            totalInvested = totalCost,
            totalPnl = totalPnl,
            pnlPercentage = pnlPercentage,
            lastUpdated = System.currentTimeMillis()
        )
        portfolioDao.updatePortfolio(updatedPortfolio)
    }
}
