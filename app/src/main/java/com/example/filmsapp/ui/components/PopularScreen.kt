package com.example.filmsapp.ui.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.filmsapp.R
import com.example.filmsapp.ui.models.Movie
import com.example.filmsapp.ui.models.samples.SampleUiData
import com.google.accompanist.drawablepainter.rememberDrawablePainter


@Composable
fun PopularScreen(
    modifier: Modifier = Modifier,
    cards: List<Movie>,
    onSearchClicked:() -> Unit,
    onCardClicked:(Movie) -> Unit,
    onBottomButtonClicked:(Screen) -> Unit
){
    MainScreen(
        modifier = modifier,
        header = { MainHeader(title = stringResource(id = R.string.popular_title), onClicked = onSearchClicked)},
        footer = {MainFooter(modifier = Modifier.padding(top = 10.dp), screen = Screen.Popular, onClicked = onBottomButtonClicked)})
    {
        LazyColumn(modifier = Modifier.padding(top = 21.dp)){
            items(cards){ card ->
                MovieCard(
                    title = card.title,
                    genres = card.genres,
                    year = card.year,
                    image = BitmapPainter(card.image.asImageBitmap()),
                    isFavourite = card.isFavourite,
                    onClicked = {onCardClicked(card)}) {
                }
                Spacer(modifier = Modifier.height(12.dp))
            }
        }
    }
}

@Preview
@Composable
fun PopularScreenPreview(){
    PopularScreen(
        cards = MutableList(10){ SampleUiData.getMovieCard(LocalContext.current) },
        onSearchClicked = {  },
        onCardClicked = {},
        onBottomButtonClicked = {}
    )
}