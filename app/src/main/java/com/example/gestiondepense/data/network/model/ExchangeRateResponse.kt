package com.example.gestiondepense.data.network.model

data class ExchangeRateResponse(
    val base: String,
    val date: String,
    val rates: Map<String, Double>
)