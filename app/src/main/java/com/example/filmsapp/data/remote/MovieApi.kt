package com.example.filmsapp.data.remote

import com.example.filmsapp.data.remote.dto.FilmsDto
import com.example.filmsapp.data.remote.dto.MovieDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApi {
    @GET("api/v2.2/films/top")
    @AuthToken
    suspend fun getTopMovies(
        @Query("type") type: String = "TOP_100_POPULAR_FILMS",
        @Query("page") page: Int = 1
    ): FilmsDto

    @GET("api/v2.2/films/{id}")
    @AuthToken
    suspend fun getFilmDescription(
        @Path("id") id: Int
    ): MovieDto
}

@Target(AnnotationTarget.FUNCTION)
annotation class AuthToken