package com.example.filmsapp.data.repository

import android.annotation.SuppressLint
import android.util.Log
import com.example.filmsapp.data.remote.MovieApi
import com.example.filmsapp.ui.models.Movie
import timber.log.Timber
import javax.inject.Inject

interface MovieRepository {
    suspend fun getPopularFilms(): List<Movie>
    suspend fun getFilmInfo(): Unit
}

class MovieRepositoryImpl @Inject constructor(
    private val movieApi: MovieApi
): MovieRepository {
    @SuppressLint("LogNotTimber")
    override suspend fun getPopularFilms(): List<Movie> {
        val newFilms = movieApi.getTopMovies()
        newFilms.films.forEach {
            Timber.d("""
                |name = ${it.name}
                |year = ${it.year}
                |url = ${it.url}
            """.trimMargin())
        }
        return listOf()
    }

    override suspend fun getFilmInfo(): Unit {
        val movie = movieApi.getFilmDescription(1111)
    }
}