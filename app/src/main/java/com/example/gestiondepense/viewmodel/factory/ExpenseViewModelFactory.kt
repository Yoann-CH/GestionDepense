package com.example.gestiondepense.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.gestiondepense.data.repository.ExpenseRepository
import com.example.gestiondepense.viewmodel.ExpenseViewModel

class ExpenseViewModelFactory(private val expenseRepository: ExpenseRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ExpenseViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ExpenseViewModel(expenseRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
