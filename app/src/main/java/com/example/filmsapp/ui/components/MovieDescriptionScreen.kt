package com.example.filmsapp.ui.components

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import com.example.filmsapp.R
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.filmsapp.ui.models.Movie
import com.example.filmsapp.ui.models.samples.SampleUiData
import com.example.filmsapp.ui.theme.BackgroundBlue
import com.example.filmsapp.ui.theme.Roboto
import com.example.filmsapp.ui.theme.TextBlack
import com.google.accompanist.drawablepainter.rememberDrawablePainter
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun MovieDescription(
    modifier: Modifier = Modifier,
    movie: Movie,
    navigatedFrom: Screen = Screen.Popular(),
    onBackPressed:(from: Screen, current: Screen) -> Unit
){
    val scrollState = rememberScrollState()
    BackHandler(
        enabled = true,
        onBack = { onBackPressed(navigatedFrom, Screen.Info(movie, navigatedFrom)) }
    )
    Box(modifier = Modifier.fillMaxSize()
    ){
        Column(
            modifier = modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
        ) {
            Image(
                modifier = Modifier.fillMaxWidth(),
                painter = BitmapPainter(movie.image.asImageBitmap()),
                contentDescription = "Film image",
                contentScale = ContentScale.FillWidth
            )
            Column(modifier = Modifier
                .padding(top = 20.dp)
                .padding(horizontal = 31.dp)
            ) {
                Text(
                    text = movie.title,
                    fontFamily = FontFamily.Roboto,
                    fontWeight = FontWeight.Medium,
                    fontSize = 20.sp
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = movie.description,
                    fontFamily = FontFamily.Roboto,
                    fontWeight = FontWeight.Normal,
                    color = TextBlack,
                    fontSize = 14.sp
                )
                Spacer(modifier = Modifier.height(15.dp))
                PointWithList(title = stringResource(id = R.string.genres_title), list = movie.genres)
                Spacer(modifier = Modifier.height(8.dp))
                PointWithList(title = stringResource(id = R.string.countries_title), list = movie.countries)
                Spacer(modifier = Modifier.height(32.dp))
            }
        }
        IconButton(
            modifier = Modifier
                .offset(x = 6.dp, y = 40.dp)
                .scale(1.2f),
            onClick = { onBackPressed(navigatedFrom, Screen.Info(movie,navigatedFrom)) }
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_back_arrow),
                tint = BackgroundBlue,
                contentDescription = "back arrow",
            )
        }
    }
}

@Composable
fun PointWithList(
    modifier: Modifier = Modifier,
    title: String,
    list: List<String>
){
    Row(modifier = modifier.fillMaxWidth()) {
        Text(
            text = "$title: ",
            fontFamily = FontFamily.Roboto,
            fontWeight = FontWeight.Medium,
            color = TextBlack,
            fontSize = 14.sp
        )
        Text(
            text = list.reduce { acc, s -> "$acc, $s" },
            fontFamily = FontFamily.Roboto,
            fontWeight = FontWeight.Normal,
            color = TextBlack,
            fontSize = 14.sp
        )
    }
}

@Preview(showBackground = true)
@Composable
fun MovieDescriptionPreview(){
  MovieDescription(
      movie = SampleUiData.getMovieCard(LocalContext.current),
      onBackPressed = {_,_ ->}
  )
}