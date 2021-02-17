package com.example.myapplication.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.myapplication.data.dao.PokemonDao
import com.example.myapplication.data.dao.RemoteKeyDao
import com.example.myapplication.data.entity.Pokemon
import com.example.myapplication.data.entity.RemoteKey


@Database(entities = [Pokemon::class, RemoteKey::class], version = 1, exportSchema = false)
abstract class PokemonDataBase : RoomDatabase() {

    abstract fun pokemonDao() : PokemonDao

    abstract fun RemoteKeyDao() : RemoteKeyDao

    companion object{
        @Volatile
        private var INSTANCE : PokemonDataBase? = null

        fun getDatabase(context : Context) : PokemonDataBase {
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext.applicationContext,
                    PokemonDataBase::class.java,
                    "pokemon_database"
                ).build()

                INSTANCE = instance
                instance
            }
        }
    }

}