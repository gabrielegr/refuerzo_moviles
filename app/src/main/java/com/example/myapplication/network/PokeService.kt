package com.example.myapplication.network

import com.example.myapplication.data.entity.Pokemon
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

private const val POKE_URL = "https://pokeapi.co/api/v2/"

private val interceptor = HttpLoggingInterceptor().apply {
    level =HttpLoggingInterceptor.Level.BODY
}

private val client = OkHttpClient.Builder().addInterceptor(interceptor).build()

private val retrofit = Retrofit.Builder()
    .baseUrl(POKE_URL)
    .client(client)
    .addConverterFactory(GsonConverterFactory.create())
    .build()

interface PokeService {
    @GET("pokemon/{query}")
    suspend fun getPokemon(@Path("query")query : String) : Pokemon
}

object PokeAPI {
    val retrofitService : PokeService by lazy {
        retrofit.create(PokeService::class.java)
    }
}