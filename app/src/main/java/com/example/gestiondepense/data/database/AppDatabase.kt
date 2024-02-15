package com.example.gestiondepense.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.gestiondepense.data.database.converter.Converters
import com.example.gestiondepense.data.database.dao.ExpenseDao
import com.example.gestiondepense.data.database.dao.UserProfileDao
import com.example.gestiondepense.data.database.entity.Expense
import com.example.gestiondepense.data.database.entity.UserProfile

@Database(entities = [Expense::class, UserProfile::class], version = 2, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun expenseDao(): ExpenseDao
    abstract fun userProfileDao(): UserProfileDao

    companion object {
        @Volatile private var instance: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(context, AppDatabase::class.java, "app_database")
                .fallbackToDestructiveMigration()
                .build()
        }
    }
}

