package com.example.myweatherapp.data

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private const val  baseURL = "https://api.openweathermap.org/data/2.5/"

    private fun getInstance(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseURL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val weatherAPI: APIService = getInstance().create(APIService::class.java)

    // Helper method to get the full URL
    fun getFullUrl(endpoint: String, queryParams: Map<String, String> = emptyMap()): String {
        val builder = StringBuilder(baseURL).append(endpoint)

        if (queryParams.isNotEmpty()) {
            builder.append("?")
            queryParams.entries.joinToString("&").let {
                builder.append(it)
            }
        }

        return builder.toString()
    }
}