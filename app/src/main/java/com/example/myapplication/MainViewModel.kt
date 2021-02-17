package com.example.myapplication

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.example.myapplication.data.PokemonDataBase
import com.example.myapplication.data.entity.Pokemon
import com.example.myapplication.network.PokeAPI
import com.example.myapplication.network.PokemonMediator
import com.example.myapplication.repository.PokemonRepository
import kotlinx.coroutines.launch
import timber.log.Timber
import java.lang.Exception

class MainViewModel(
    private val pokemonRepository: PokemonRepository,
    database: PokemonDataBase
): ViewModel() {

    @OptIn(ExperimentalPagingApi::class)
    val pager = Pager(
        config = PagingConfig(pageSize = 50),
        remoteMediator = PokemonMediator(database, PokeAPI)
    ){
        pokemonRepository.getSource()
    }

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