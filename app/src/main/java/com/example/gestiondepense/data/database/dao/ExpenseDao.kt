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
    fun insert(expense: Expense): Long

    @Update
    fun update(expense: Expense)

    @Delete
    fun delete(expense: Expense)

    @Query("SELECT * FROM Expense")
    fun getAllExpenses(): List<Expense>

    @Query("SELECT * FROM Expense WHERE id = :id")
    fun getExpenseById(id: Int): Expense

    @Query("SELECT * FROM Expense WHERE category = :category")
    fun getExpensesByCategory(category: String): List<Expense>

    @Query("SELECT * FROM Expense WHERE date BETWEEN :startDate AND :endDate")
    fun getExpensesBetweenDates(startDate: Date, endDate: Date): List<Expense>

    @Query("SELECT * FROM Expense WHERE isFavorite = 1")
    fun getFavoriteExpenses(): List<Expense>
}
