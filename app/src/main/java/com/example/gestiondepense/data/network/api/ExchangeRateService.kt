package com.example.gestiondepense.data.network.api

import com.example.gestiondepense.data.network.model.ExchangeRateResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.Response

interface ExchangeRateService {
    @GET("latest/{base}")
    suspend fun getLatestRates(@Path("base") baseCurrency: String): Response<ExchangeRateResponse>
}