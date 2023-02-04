package com.example.filmsapp.ui.viewmodel

import androidx.annotation.IdRes
import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.filmsapp.R
import com.example.filmsapp.data.repository.MovieRepository
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
    val state: StateFlow<MainScreenState> = flow<MainScreenState> {
        val movies = movieRepository.getPopularFilms()
    }
        .stateIn(viewModelScope, SharingStarted.Lazily, MainScreenState.StillLoading(R.string.popular_title,R.string.loading_text))

    init {
        viewModelScope.launch {
            movieRepository.getFilmInfo()
        }
    }
}


sealed class MainScreenState(val destination: String){
    data class NoConnection(@StringRes val text: Int): MainScreenState(ScreensDestination.noConnection)
    data class StillLoading(@StringRes val title: Int,@StringRes val text: Int): MainScreenState(ScreensDestination.loading)
    data class Favourites(@StringRes val title: Int, val movies: List<Movie>): MainScreenState(ScreensDestination.favourites)
    data class Popular(@StringRes val title: Int, val movies: List<Movie>): MainScreenState(ScreensDestination.popular)
}