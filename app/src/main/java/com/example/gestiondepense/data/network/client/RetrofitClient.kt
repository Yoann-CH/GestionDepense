package com.example.gestiondepense.data.network.client

import com.example.gestiondepense.data.network.api.ExchangeRateService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "https://v6.exchangerate-api.com/v6/4f1aacec62cf1b993a4d2c82/"

    val instance: ExchangeRateService by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofit.create(ExchangeRateService::class.java)
    }
}