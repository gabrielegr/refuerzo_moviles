package com.example.myapplication

import android.app.Application
import com.example.myapplication.data.PokemonDataBase
import com.example.myapplication.repository.PokemonRepository
import timber.log.Timber

class MyApplication : Application() {

    val database by lazy { PokemonDataBase.getDatabase(this) }
    val pokemonRepository by lazy { PokemonRepository(database.pokemonDao())}

    override fun onCreate(){
        super.onCreate()
        Timber.plant(Timber.DebugTree())

    }
}