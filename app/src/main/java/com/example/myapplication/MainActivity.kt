package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.viewModels
import com.example.myapplication.data.entity.Pokemon

class MainActivity : AppCompatActivity() {

    private val viewModelFactory by lazy {
        val myApplication = application as MyApplication
        val pokemonRepository = myApplication.pokemonRepository
        MainViewModelFactory(pokemonRepository)
    }

    private val viewModel : MainViewModel by viewModels {
       viewModelFactory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        onFindPokemon()

        viewModel.pokemonInfo.observe(this){
            val nameView = findViewById<TextView>(R.id.pokemon_name_text)
            nameView.text = it.name
        }


    }

    fun onFindPokemon(){
        val button = findViewById<Button>(R.id.action_search_button)
        button.setOnClickListener {
            val queryView = findViewById<EditText>(R.id.pokemon_query_text)
            val query = queryView.text.toString()
            viewModel.getPokemon(query)
        }
    }
}