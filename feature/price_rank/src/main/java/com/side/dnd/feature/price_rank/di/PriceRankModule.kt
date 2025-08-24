package com.side.dnd.feature.price_rank.di

import com.side.dnd.feature.price_rank.data.api.PriceRankApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import network.di.PriceRankRetrofit
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PriceRankModule {
    
    @Provides
    @Singleton
    fun providePriceRankApi(
        @PriceRankRetrofit retrofit: Retrofit
    ): PriceRankApi {
        return retrofit.create(PriceRankApi::class.java)
    }
}
