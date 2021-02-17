package com.example.myapplication

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.data.PokemonDataBase
import com.example.myapplication.repository.PokemonRepository
import java.lang.Exception

class MainViewModelFactory(
    private val repository: PokemonRepository,
    private val database: PokemonDataBase
    ) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(MainViewModel::class.java)){
            return MainViewModel(repository, database) as T
        }

        throw Exception("View model class unknown")
    }
}