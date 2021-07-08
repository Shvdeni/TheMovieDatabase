package com.example.themoviedatabase.models

data class MovieGenres(
    val genres: List<GenreM>

)

data class GenreM(
    val id: Int,
    val name: String
)