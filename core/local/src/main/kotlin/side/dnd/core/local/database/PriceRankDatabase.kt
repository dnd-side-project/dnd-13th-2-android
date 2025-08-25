package side.dnd.core.local.database

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import android.content.Context
import side.dnd.core.local.dao.CategoryDao
import side.dnd.core.local.model.CategoryEntity
import side.dnd.core.local.model.ItemEntity
import side.dnd.core.local.model.KindEntity

@Database(
    entities = [
        CategoryEntity::class,
        ItemEntity::class,
        KindEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class PriceRankDatabase : RoomDatabase() {
    
    abstract fun categoryDao(): CategoryDao
    
    companion object {
        private const val DATABASE_NAME = "price_rank_database"
        
        @Volatile
        private var INSTANCE: PriceRankDatabase? = null
        
        fun getInstance(context: Context): PriceRankDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    PriceRankDatabase::class.java,
                    DATABASE_NAME
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
