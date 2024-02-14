package com.example.gestiondepense.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.gestiondepense.data.database.dao.ExpenseDao
import com.example.gestiondepense.data.database.dao.UserProfileDao
import com.example.gestiondepense.data.database.entity.Expense
import com.example.gestiondepense.data.database.entity.UserProfile

@Database(entities = [Expense::class, UserProfile::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun expenseDao(): ExpenseDao
    abstract fun userProfileDao(): UserProfileDao
}

