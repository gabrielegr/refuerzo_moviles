package com.example.myapplication.repository

import com.example.myapplication.data.dao.PokemonDao
import com.example.myapplication.data.entity.Pokemon
import com.example.myapplication.network.PokeAPI

class PokemonRepository(private val pokemonDao: PokemonDao) {
private val API = PokeAPI.retrofitService
    suspend fun insert(pokemon: Pokemon) = pokemonDao.insert(pokemon)
    suspend fun getPokemon(query: String) = API.getPokemon(query)
}