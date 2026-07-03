package com.groww.papertrading.di

import android.content.Context
import androidx.room.Room
import com.groww.papertrading.data.database.PaperTradingDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun providePaperTradingDatabase(
        @ApplicationContext context: Context
    ): PaperTradingDatabase =
        Room.databaseBuilder(
            context,
            PaperTradingDatabase::class.java,
            "paper_trading_db"
        )
            .fallbackToDestructiveMigration()
            .build()

    @Singleton
    @Provides
    fun provideUserDao(database: PaperTradingDatabase) = database.userDao()

    @Singleton
    @Provides
    fun provideStockDao(database: PaperTradingDatabase) = database.stockDao()

    @Singleton
    @Provides
    fun provideTradeDao(database: PaperTradingDatabase) = database.tradeDao()

    @Singleton
    @Provides
    fun providePortfolioDao(database: PaperTradingDatabase) = database.portfolioDao()

    @Singleton
    @Provides
    fun provideHoldingDao(database: PaperTradingDatabase) = database.holdingDao()

    @Singleton
    @Provides
    fun provideWatchlistDao(database: PaperTradingDatabase) = database.watchlistDao()
}
