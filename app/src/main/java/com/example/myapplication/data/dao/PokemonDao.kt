package com.example.myapplication.data.dao

import androidx.lifecycle.LiveData
import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.myapplication.data.entity.Pokemon

@Dao
interface PokemonDao {
    @Insert(onConflict =OnConflictStrategy.REPLACE)
    suspend fun insert(pokemon: Pokemon)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(pokemons : List<Pokemon>)

    @Query(  "SELECT * FROM pokemon_table")
    fun findAll() : LiveData<List<Pokemon>>

    @Query ("SELECT * FROM pokemon_table")
    fun getSource(): PagingSource<Int,Pokemon>

    @Query( "DELETE  FROM pokemon_table WHERE id = :id")
    fun delete(id: Int)

    @Query( "DELETE  FROM pokemon_table")
    fun deleteAll()
}