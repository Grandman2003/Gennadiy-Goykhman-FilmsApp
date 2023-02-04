package com.example.filmsapp.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.bumptech.glide.Glide
import com.example.filmsapp.ui.components.FavouriteScreen
import com.example.filmsapp.ui.components.PopularScreen
import com.example.filmsapp.ui.models.Movie
import com.example.filmsapp.ui.models.ScreensDestination
import com.example.filmsapp.ui.theme.FilmsAppTheme
import com.example.filmsapp.ui.viewmodel.MainScreenState
import com.example.filmsapp.ui.viewmodel.MainViewModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val statusBarState = rememberSystemUiController()
            val screenState by viewModel.state.collectAsState()
            LaunchedEffect(key1 = statusBarState) {
                statusBarState.setStatusBarColor(color = Color.Transparent)
            }
            Surface(
                modifier = Modifier.fillMaxSize()
            ) {

            }
        }
    }
}

@Composable
fun ScreenHolder(
    modifier: Modifier,
    screenState: MainScreenState
) {
    when (screenState) {
        is MainScreenState.NoConnection -> {}
        is MainScreenState.StillLoading -> {}
        is MainScreenState.Popular ->
            PopularScreen(
                cards = (screenState as MainScreenState.Popular).movies,
                onSearchClicked = { /*TODO*/ },
                onCardClicked = { },
                onBottomButtonClicked = { }
            )
        is MainScreenState.Favourites ->
            FavouriteScreen(
                cards = (screenState as MainScreenState.Favourites).movies,
                onSearchClicked = { /*TODO*/ },
                onCardClicked = { },
                onBottomButtonClicked = { }
            )
    }
}
