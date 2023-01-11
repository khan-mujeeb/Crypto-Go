package com.example.cryptogo.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.cryptogo.model.Coin

@Database(
    entities = [Coin::class],
    version = 1,
    exportSchema = false
)

abstract class CoinDatabase : RoomDatabase() {
    abstract fun getCoinDao(): Dao

    companion object {
        private const val DB_NAME = "note_database.db"
        @Volatile
        private var instance: CoinDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also {
                instance = it
            }
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            CoinDatabase::class.java,
            DB_NAME
        ).build()
    }
}
