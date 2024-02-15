package com.example.gestiondepense.data.repository

import com.example.gestiondepense.data.database.dao.ExpenseDao
import com.example.gestiondepense.data.database.entity.Expense

class ExpenseRepository(private val expenseDao: ExpenseDao) {

    suspend fun insert(expense: Expense) {
        expenseDao.insert(expense)
    }

    suspend fun update(expense: Expense) {
        expenseDao.update(expense)
    }

    suspend fun delete(expense: Expense) {
        expenseDao.delete(expense)
    }

    suspend fun getAllExpenses(): List<Expense> {
        return expenseDao.getAllExpenses()
    }

    suspend fun getExpenseById(id: Int?): Expense {
        return expenseDao.getExpenseById(id)
    }

    suspend fun getExpensesByCategory(category: String): List<Expense> {
        return expenseDao.getExpensesByCategory(category)
    }

    suspend fun getFavoriteExpenses(): List<Expense> {
        return expenseDao.getFavoriteExpenses()
    }
}
