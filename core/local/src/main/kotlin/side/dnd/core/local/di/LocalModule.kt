package side.dnd.core.local.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import side.dnd.core.local.dao.CategoryDao
import side.dnd.core.local.database.PriceRankDatabase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalModule {
    
    @Provides
    @Singleton
    fun providePriceRankDatabase(
        @ApplicationContext context: Context
    ): PriceRankDatabase {
        return PriceRankDatabase.getInstance(context)
    }
    
    @Provides
    @Singleton
    fun provideCategoryDao(
        database: PriceRankDatabase
    ): CategoryDao {
        return database.categoryDao()
    }
}
