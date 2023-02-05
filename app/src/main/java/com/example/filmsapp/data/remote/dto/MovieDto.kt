package com.example.filmsapp.data.remote.dto

import com.squareup.moshi.Json

data class FilmsDto (
    @field: Json(name = "pagesCount")
    val pagesCount: Int,
    @field: Json(name = "films")
    val films: List<MovieDto>
    )

data class MovieDto (
    @field: Json(name = "filmId")
    val filmId: Int,
    @field: Json(name = "nameRu")
    val name: String,
    @field: Json(name = "countries")
    val countries: List<CountryDto>,
    @field: Json(name = "genres")
    val genres: List<GenreDto>,
    @field: Json(name = "description")
    val description: String = "",
    @field: Json(name = "year")
    val year: Int,
    @field: Json(name = "posterUrl")
    val url: String,
    )

data class CountryDto(
    @field: Json(name = "country")
    val country: String,
)
data class GenreDto(
    @field: Json(name = "genre")
    val genre: String,
)
