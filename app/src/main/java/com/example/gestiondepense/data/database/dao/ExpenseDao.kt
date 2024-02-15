package com.example.gestiondepense.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.gestiondepense.data.database.entity.Expense
import java.util.Date

@Dao
interface ExpenseDao {
    @Insert
    suspend fun insert(expense: Expense): Long

    @Update
    suspend fun update(expense: Expense)

    @Delete
    suspend fun delete(expense: Expense)

    @Query("SELECT * FROM Expense")
    suspend fun getAllExpenses(): List<Expense>

    @Query("SELECT * FROM Expense WHERE id = :id")
    suspend fun getExpenseById(id: Int): Expense

    @Query("SELECT * FROM Expense WHERE category = :category")
    suspend fun getExpensesByCategory(category: String): List<Expense>

    @Query("SELECT * FROM Expense WHERE date BETWEEN :startDate AND :endDate")
    suspend fun getExpensesBetweenDates(startDate: Date, endDate: Date): List<Expense>

    @Query("SELECT * FROM Expense WHERE isFavorite = 1")
    suspend fun getFavoriteExpenses(): List<Expense>
}
