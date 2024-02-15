package com.example.gestiondepense.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import com.example.gestiondepense.data.repository.ExpenseRepository
import com.example.gestiondepense.data.database.entity.Expense
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ExpenseViewModel(private val expenseRepository: ExpenseRepository) : ViewModel() {

    private val _allExpenses = MutableStateFlow<List<Expense>>(emptyList())
    val allExpenses: StateFlow<List<Expense>> = _allExpenses

    private val _expenseDetails = MutableStateFlow<Expense?>(null)
    val expenseDetails: StateFlow<Expense?> = _expenseDetails

    fun insert(expense: Expense) {
        viewModelScope.launch {
            try {
                expenseRepository.insert(expense)
                val updatedExpenses = expenseRepository.getAllExpenses()
                _allExpenses.value = updatedExpenses
            } catch (e: Exception) {
                // Gérer l'exception si nécessaire
            }
        }
    }

    fun update(expense: Expense)  {
        viewModelScope.launch {
            try {
                expenseRepository.update(expense)
                val updatedExpenses = expenseRepository.getAllExpenses()
                _allExpenses.value = updatedExpenses
            } catch (e: Exception) {
                Log.e("ExpenseViewModel", "Failed to update expense", e)
            }
        }
    }

    fun delete(expense: Expense) {
        viewModelScope.launch {
            try {
                expenseRepository.delete(expense)
                val updatedExpenses = expenseRepository.getAllExpenses()
                _allExpenses.value = updatedExpenses
            } catch (e: Exception) {
                // Gérer l'exception si nécessaire
            }
        }
    }

    suspend fun getExpenses() {
        _allExpenses.value = expenseRepository.getAllExpenses()
    }

    fun getExpensesById(id: Int?) = viewModelScope.launch {
        _expenseDetails.value = expenseRepository.getExpenseById(id)
    }

    fun resetExpenseDetails() = run { _expenseDetails.value = null }
}