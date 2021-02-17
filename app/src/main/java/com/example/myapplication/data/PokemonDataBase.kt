package com.example.myapplication.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.myapplication.data.dao.PokemonDao
import com.example.myapplication.data.entity.Pokemon


@Database(entities = [Pokemon::class], version = 1, exportSchema = false)
abstract class PokemonDataBase : RoomDatabase() {

    abstract fun pokemonDao() : PokemonDao

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