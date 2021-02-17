package com.example.myapplication.network

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.myapplication.data.PokemonDataBase
import com.example.myapplication.data.entity.Pokemon
import com.example.myapplication.data.entity.RemoteKey
import timber.log.Timber

const val PAGE_KEY = "POKEMON"

@OptIn(ExperimentalPagingApi::class)
class PokemonMediator(private val database: PokemonDataBase,
private val API: PokeAPI) : RemoteMediator<Int, Pokemon>() {

    val remoteKeyDao = database.RemoteKeyDao()
    val pokemonDao = database.pokemonDao()

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, Pokemon>
    ): MediatorResult {
        return try{
            val loadKey = when(loadType){
                LoadType.REFRESH -> null
                LoadType.PREPEND ->
                    return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND ->{
                    val remoteKey = database.withTransaction {
                        remoteKeyDao.remoteKeyByQuery(PAGE_KEY)
                    }

                    if(remoteKey.nextKey == null){
                        return MediatorResult.Success(endOfPaginationReached = true)
                    }
                    remoteKey.nextKey}
            }
            val response = if(loadKey == null) {
                API.retrofitService.getListPokemon(50)
            }else{API.retrofitService.getListPokemonByUrl(loadKey)}

            val pokemons = response.results.map {
                API.retrofitService.getPokemonByUrl(it.url)
}
            database.withTransaction {
                if(loadType == LoadType.REFRESH){
                    pokemonDao.deleteAll()
                    remoteKeyDao.deleteByQuery(PAGE_KEY)
                }
                remoteKeyDao.insertOrReplace(RemoteKey(PAGE_KEY,response.next))
                pokemonDao.insertAll(pokemons)
            }
            MediatorResult.Success(
                endOfPaginationReached = response.next.isNullOrBlank()
            )
        }catch (e: Exception) {
            Timber.d(e)
            MediatorResult.Error(e)
        }
    }
}