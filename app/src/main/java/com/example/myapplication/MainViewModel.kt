package com.example.myapplication

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.entity.Pokemon
import com.example.myapplication.repository.PokemonRepository
import kotlinx.coroutines.launch
import timber.log.Timber
import java.lang.Exception

class MainViewModel(
    private val pokemonRepository: PokemonRepository
): ViewModel() {

    private var _pokemonInfo = MutableLiveData<Pokemon>()
    val pokemonInfo : LiveData<Pokemon>
    get()= _pokemonInfo

    fun insert(pokemon: Pokemon){
        viewModelScope.launch {
            pokemonRepository.insert(pokemon)
        }
    }

    fun getPokemon(query: String){
        viewModelScope.launch {
            try{
            val pokemon = pokemonRepository.getPokemon(query)
                _pokemonInfo.value = pokemon
        }catch (e:Exception){
            Timber.d(e)
        }
        }
    }
}