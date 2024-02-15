package com.example.gestiondepense.viewmodel

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

    fun insert(expense: Expense) = viewModelScope.launch {
        expenseRepository.insert(expense)
    }

    fun update(expense: Expense) = viewModelScope.launch {
        expenseRepository.update(expense)
    }

    fun delete(expense: Expense) = viewModelScope.launch {
        expenseRepository.delete(expense)
    }
}