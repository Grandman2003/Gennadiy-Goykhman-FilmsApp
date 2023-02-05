package com.example.filmsapp.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.filmsapp.R
import com.example.filmsapp.ui.models.Movie
import com.example.filmsapp.ui.theme.BackgroundBlue
import com.example.filmsapp.ui.theme.Roboto

@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    header: @Composable()() -> Unit,
    footer: @Composable()() -> Unit,
    content: @Composable()() -> Unit,
){
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Column(modifier = Modifier
            .fillMaxSize()
            .weight(1f)
        ) {
            header()
            content()
        }
        footer()
    }
}

@Composable
fun MainHeader(
    modifier: Modifier = Modifier,
    title: String,
    onClicked: () -> Unit
){
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            fontFamily = FontFamily.Roboto,
            fontWeight = FontWeight.Medium,
            fontSize = 32.sp
        )
        IconButton(onClick = onClicked) {
            Icon(
                painter = painterResource(id = R.drawable.ic_search),
                tint = BackgroundBlue,
                contentDescription = "search button",
            )
        }
    }
}

@Composable
fun MainFooter(
    modifier: Modifier = Modifier,
    screen: Screen,
    onClicked: (Screen) -> Unit
){
    Row(modifier = modifier
        .fillMaxWidth()
        .wrapContentHeight()
    ) {
        BlueToggleButton(
            modifier = Modifier
                .weight(weight = 1.0f),
            text = stringResource(id = R.string.popular_title),
            isChoosen = screen is Screen.Popular,
            onClicked = { onClicked(Screen.Popular()) }
        )
        Spacer(modifier = Modifier.width(14.dp))
        BlueToggleButton(
            modifier = Modifier
                .weight(weight = 1.0f),
            text = stringResource(id = R.string.favourite_title),
            isChoosen = screen is Screen.Favourites,
            onClicked = { onClicked(Screen.Favourites()) }
        )
    }
}

@Preview
@Composable
fun MainFooterPreview(){
    MainFooter(
        screen = Screen.Popular(),
        onClicked = {},
    )
}

@Preview
@Composable
fun MainHeader(){
    MainHeader(
        title = "Popular",
        onClicked = {}
    )
}

sealed interface Screen {
    data class Favourites(var fromInfo: Boolean = true): Screen
    data class Popular(var fromInfo: Boolean = true): Screen
    data class Loading(val from: Screen? = null): Screen
    data class Error(val errorOnScreen: Screen = Loading()): Screen
    data class Info(val movie: Movie, val from: Screen = Popular()): Screen
}


