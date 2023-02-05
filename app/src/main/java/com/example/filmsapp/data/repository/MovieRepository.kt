package com.example.filmsapp.data.repository

import com.bumptech.glide.RequestManager
import com.example.filmsapp.data.db.MovieDao
import com.example.filmsapp.data.db.MovieEntity
import com.example.filmsapp.data.remote.MovieApi
import com.example.filmsapp.data.remote.mappers.MovieMappers.toMovie
import com.example.filmsapp.data.remote.mappers.MovieMappers.toMovieEntity
import com.example.filmsapp.domain.model.DataResult
import com.example.filmsapp.ui.models.Movie
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import timber.log.Timber
import javax.inject.Inject
import kotlin.coroutines.coroutineContext

interface MovieRepository {
    fun getPopularFilms(): Flow<DataResult<List<Movie>>>
    suspend fun getFilmInfo(oldMovie: Movie): DataResult<Movie>
    suspend fun changeFavouriteState(movie: Movie)
    suspend fun retryUpdate()
}

class MovieRepositoryImpl @Inject constructor(
    private val movieApi: MovieApi,
    private val movieDao: MovieDao,
    private val requestManager: RequestManager
) : MovieRepository {

    override fun getPopularFilms(): Flow<DataResult<List<Movie>>> =
        movieDao
            .getAllMovies()
            .map { it.map { entity -> entity.toMovie() } }
            .combineTransform(flow { emit(requestFilms()) }) { movies, updateState ->
                if (movies.isEmpty() && updateState is UpdateState.Error) {
                    emit(DataResult.Error(message = "Unable to update movies"))
                } else if (movies.isNotEmpty()) {
                    emit(DataResult.Success(movies))
                }
            }


    override suspend fun getFilmInfo(oldMovie: Movie): DataResult<Movie> {
        if (oldMovie.description.isNotBlank()) return DataResult.Success(
            CompletableDeferred(
                oldMovie
            ).await()
        )
        return try {
            DataResult.Success(coroutineScope {
                val result = async(Dispatchers.IO) {
                    val movie =
                        movieApi.getFilmDescription(oldMovie.id).toMovieEntity(requestManager)
                    movieDao.updateMovie(movie)
                    movie.toMovie()
                }
                result
            }.await())
        } catch (e: Exception) {
            e.printStackTrace()
            DataResult.Error(message = e.localizedMessage ?: "")
        }
    }

    override suspend fun changeFavouriteState(movie: Movie) {
        val currentMovie = movieDao.getMovieById(movie.id)
        currentMovie.isFavourite = if (currentMovie.isFavourite == 0) 1 else 0
        movieDao.updateMovie(currentMovie)
    }

    override suspend fun retryUpdate() {
        requestFilms()
    }

    private suspend fun requestFilms(): UpdateState =
        when (val newFilms = updateFilms()) {
            is DataResult.Success -> {
                val favMovies = movieDao.getAllMovies().first().filter { it.isFavourite == 1 }
                movieDao.deleteTable()
                val films = mutableListOf<MovieEntity>().apply {
                    addAll(newFilms.data ?: emptyList())
                    addAll(favMovies)
                }
                movieDao.addMovie(movie = films.toTypedArray())
                UpdateState.Success
            }
            is DataResult.Error -> {
                UpdateState.Error
            }
        }

    private suspend fun updateFilms(): DataResult<List<MovieEntity>> =
        withContext(Dispatchers.IO) {
            try {
                DataResult.Success(
                    movieApi.getTopMovies().films.map { dto ->
                        Timber.d(
                            """
                        |name = ${dto.name}
                        |year = ${dto.year}
                        |url = ${dto.url}
                        """.trimMargin()
                        )
                        dto.toMovieEntity(requestManager)
                    }
                )
            } catch (e: Exception) {
                e.printStackTrace()
                DataResult.Error(message = e.message ?: "")
            }
        }

}

sealed interface UpdateState {
    object Success : UpdateState
    object Error : UpdateState
}