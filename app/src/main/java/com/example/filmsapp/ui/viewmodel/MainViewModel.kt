package com.example.filmsapp.ui.viewmodel

import androidx.annotation.IdRes
import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.filmsapp.R
import com.example.filmsapp.data.repository.MovieRepository
import com.example.filmsapp.domain.model.DataResult
import com.example.filmsapp.ui.components.Screen
import com.example.filmsapp.ui.models.Movie
import com.example.filmsapp.ui.models.ScreensDestination
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val movieRepository: MovieRepository
): ViewModel() {
    private val currentScreen: MutableStateFlow<Screen> = MutableStateFlow(Screen.Popular)
    val state: StateFlow<MainScreenState> =
        movieRepository
            .getPopularFilms()
            .combineTransform(currentScreen){ result, screen ->
                when {
                    result is DataResult.Error -> { emit(MainScreenState.NoConnection()) }
                    screen is Screen.Popular -> {  emit(MainScreenState.Popular(movies = result.data as List<Movie>))}
                    screen is Screen.Favourites -> {  emit(MainScreenState.Favourites(movies = (result.data as List<Movie>).filter { it.isFavourite }))}
                    screen is Screen.Info -> {  emit(MainScreenState.MovieInfo(screen.movie, screen.from))}
                    screen is Screen.Loading -> { emit(MainScreenState.StillLoading())}
                }
            }
            .stateIn(viewModelScope, SharingStarted.Lazily, MainScreenState.StillLoading())

    fun changePage(screen: Screen){
        currentScreen.value = screen
    }

    fun showCardInfo(movie: Movie){
        val from = currentScreen.value
        currentScreen.value = Screen.Loading
        viewModelScope.launch {
            currentScreen.value = Screen.Info(movieRepository.getFilmInfo(movie), from = from)
        }
    }

    fun retryUpdate(){
        currentScreen.value = Screen.Loading
        viewModelScope.launch {
            movieRepository.retryUpdate()
        }
    }

    fun navigateBackTo(from : Screen){
        when(from){
            is Screen.Popular,
            is Screen.Favourites -> {currentScreen.value = from}
            else -> {}
        }
    }
}


sealed class MainScreenState(val destination: String){
    data class NoConnection(@StringRes val text: Int = R.string.internet_error_text): MainScreenState(ScreensDestination.noConnection)
    data class StillLoading(@StringRes val text: Int = R.string.loading_text): MainScreenState(ScreensDestination.loading)
    data class Favourites(@StringRes val title: Int  = R.string.favourite_title, val movies: List<Movie>): MainScreenState(ScreensDestination.favourites)
    data class Popular(@StringRes val title: Int = R.string.popular_title, val movies: List<Movie>): MainScreenState(ScreensDestination.popular)
    data class MovieInfo(val movie: Movie, val from: Screen): MainScreenState(ScreensDestination.info)
}