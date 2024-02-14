package com.example.gestiondepense.di

import android.content.Context
import androidx.room.Room
import com.example.gestiondepense.data.database.AppDatabase
import com.example.gestiondepense.data.database.dao.ExpenseDao
import com.example.gestiondepense.data.database.dao.UserProfileDao
import com.example.gestiondepense.data.network.client.RetrofitClient
import com.example.gestiondepense.data.network.api.ExchangeRateService
import com.example.gestiondepense.data.repository.ExpenseRepository
import com.example.gestiondepense.data.repository.UserProfileRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(context, AppDatabase::class.java, "nomapplication_database")
            .fallbackToDestructiveMigration()
            .build()

    @Singleton
    @Provides
    fun provideExpenseDao(db: AppDatabase): ExpenseDao = db.expenseDao()

    @Singleton
    @Provides
    fun provideProfileDao(db: AppDatabase): UserProfileDao = db.userProfileDao()

    @Singleton
    @Provides
    fun provideExchangeRateService(): ExchangeRateService = RetrofitClient.instance

    @Singleton
    @Provides
    fun provideExpenseRepository(expenseDao: ExpenseDao): ExpenseRepository =
        ExpenseRepository(expenseDao)

    @Singleton
    @Provides
    fun provideUserProfileRepository(userProfileDao: UserProfileDao): UserProfileRepository =
        UserProfileRepository(userProfileDao)
}