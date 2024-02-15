package com.example.gestiondepense.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.gestiondepense.data.network.api.ExchangeRateService
import com.example.gestiondepense.viewmodel.ExchangeRateViewModel

class ExchangeRateViewModelFactory(private val exchangeRateService: ExchangeRateService) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ExchangeRateViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ExchangeRateViewModel(exchangeRateService) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
