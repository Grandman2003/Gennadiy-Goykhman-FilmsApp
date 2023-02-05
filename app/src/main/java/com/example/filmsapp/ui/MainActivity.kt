package com.example.filmsapp.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.*
import androidx.compose.animation.core.FiniteAnimationSpec
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.bumptech.glide.Glide
import com.example.filmsapp.R
import com.example.filmsapp.ui.components.*
import com.example.filmsapp.ui.models.Movie
import com.example.filmsapp.ui.models.ScreensDestination
import com.example.filmsapp.ui.theme.BackgroundBlue
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
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            val statusBarState = rememberSystemUiController()
            val screenState by viewModel.state.collectAsState()
            LaunchedEffect(key1 = statusBarState) {
                statusBarState.setStatusBarColor(Color.Transparent)
            }
            Surface(
                modifier = Modifier.fillMaxSize()
            ) {
                ScreenHolder(
                    screenState = screenState,
                    changePageCallback = { screen -> viewModel.changePage(screen) },
                    onCardClickedCallback = { card -> viewModel.showCardInfo(card) },
                    onBackClicked = { screenFrom, current ->
                        viewModel.navigateBackTo(
                            screenFrom,
                            current,
                            this
                        )
                    },
                    onLongCardClick = { card -> viewModel.changeFavourite(card) },
                    onRetryClick = { viewModel.retryUpdate() }
                )
            }
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun ScreenHolder(
    modifier: Modifier = Modifier,
    screenState: MainScreenState,
    changePageCallback: (Screen) -> Unit,
    onCardClickedCallback: (Movie) -> Unit,
    onBackClicked: (from: Screen, current: Screen) -> Unit,
    onLongCardClick: (Movie) -> Unit,
    onRetryClick: () -> Unit = {}
) {
    AnimatedContent(
        targetState = screenState,
        modifier = modifier.fillMaxSize(),
        transitionSpec = {
            when (targetState) {
                is MainScreenState.Favourites -> {
                    if ((targetState as MainScreenState.Favourites).fromInfo) {
                        fadeIn() with fadeOut()
                    } else {
                        slideInHorizontally { fullWidth -> fullWidth } + fadeIn() with fadeOut() + slideOutHorizontally { fullWidth -> -fullWidth }
                    }
                }
                is MainScreenState.Popular -> {
                    if ((targetState as MainScreenState.Popular).fromInfo) {
                        fadeIn() with fadeOut()
                    } else {
                        slideInHorizontally { fullWidth -> -fullWidth } + fadeIn() with fadeOut() + slideOutHorizontally { fullWidth -> fullWidth }
                    }
                }
                is MainScreenState.MovieInfo -> {
                    EnterTransition.None with fadeOut()
                }
                else -> {
                    fadeIn() with fadeOut()
                }
            }
        },
        contentAlignment = Alignment.Center
    ) {
        when (screenState) {
            is MainScreenState.NoConnection -> {
                InfoDialog(
                    header = {
                        Column(
                            modifier = Modifier,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Image(
                                modifier = Modifier.fillMaxWidth(),
                                painter = painterResource(id = R.drawable.ic_cloud),
                                contentDescription = "No connection",
                                contentScale = ContentScale.Fit
                            )
                        }
                    },
                    description = stringResource(id = screenState.text),
                    onClose = { onBackClicked(Screen.Error(), Screen.Error()) },
                    isClosable = true
                )
            }
            is MainScreenState.StillLoading -> {
                InfoDialog(
                    header = {
                        CircularProgressIndicator(
                            modifier = Modifier
                                .fillMaxWidth(0.25f)
                                .padding(bottom = 45.dp),
                            color = BackgroundBlue,
                            strokeWidth = 4.dp
                        )
                    },
                    description = stringResource(id = screenState.text),
                    isClosable = screenState.from != null,
                    onClose = {
                        if (screenState.from != null) onBackClicked(
                            screenState.from,
                            Screen.Loading()
                        )
                    }
                )
            }
            is MainScreenState.Popular ->
                Box(modifier = modifier.fillMaxSize()) {
                    PopularScreen(
                        modifier = modifier
                            .padding(top = 26.dp, bottom = 48.dp),
                        cards = screenState.movies,
                        onSearchClicked = { },
                        onCardClicked = onCardClickedCallback,
                        onBottomButtonClicked = changePageCallback,
                        onLongClick = onLongCardClick
                    )
                }
            is MainScreenState.Favourites ->
                Box(modifier = modifier.fillMaxSize()) {
                    FavouriteScreen(
                        modifier = modifier
                            .padding(top = 26.dp, bottom = 48.dp),
                        cards = screenState.movies.filter { it.isFavourite },
                        onSearchClicked = { },
                        onCardClicked = onCardClickedCallback,
                        onBottomButtonClicked = changePageCallback,
                        onLongClick = onLongCardClick
                    )
                }
            is MainScreenState.MovieInfo ->
                MovieDescription(
                    modifier = modifier.padding(bottom = 48.dp),
                    movie = screenState.movie,
                    navigatedFrom = screenState.from,
                    onBackPressed = onBackClicked
                )
        }
    }
}

