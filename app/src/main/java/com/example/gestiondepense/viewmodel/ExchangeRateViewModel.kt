package com.example.gestiondepense.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import com.example.gestiondepense.data.network.api.ExchangeRateService
import com.example.gestiondepense.data.network.model.ExchangeRateResponse

class ExchangeRateViewModel(private val exchangeRateService: ExchangeRateService) : ViewModel() {

    private val _exchangeRates = MutableLiveData<ExchangeRateResponse>()
    val exchangeRates: LiveData<ExchangeRateResponse> = _exchangeRates

    fun fetchExchangeRates(baseCurrency: String) {
        viewModelScope.launch {
            try {
                val response = exchangeRateService.getLatestRates(baseCurrency)
                if (response.isSuccessful) {
                    _exchangeRates.value = response.body()
                } else {
                    // Handle API error
                }
            } catch (e: Exception) {
                // Handle network error
            }
        }
    }
}
