package com.example.myapplication.network.response

data class PokemonListResponse(
    var count : Int,
    var next : String?,
    var prev : String?,
    var results: List<PokemonMetaDataResponse>
)
