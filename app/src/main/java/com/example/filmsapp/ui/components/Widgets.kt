package com.example.filmsapp.ui.components

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.filmsapp.R
import com.example.filmsapp.ui.theme.*

@Composable
fun BlueToggleButton(
    modifier: Modifier = Modifier,
    text: String,
    isChoosen: Boolean = true,
    onClicked: () -> Unit,
) {
    Button(
        modifier = modifier,
        onClick = onClicked,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = if (!isChoosen) BackgroundBlue else BackgroundLightBlue
        ),
        shape = RoundedCornerShape(percent = 100)
    ) {
        Text(
            modifier = Modifier.padding(vertical = 5.dp),
            text = text,
            color = if (!isChoosen) Color.White else BackgroundBlue,
            fontWeight = FontWeight.Medium,
            fontFamily = FontFamily.Roboto
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MovieCard(
    modifier: Modifier = Modifier,
    title: String,
    genres: List<String>,
    year: Int,
    image: Painter,
    isFavourite: Boolean,
    onClicked: () -> Unit,
    onLongClicked: () -> Unit
) {
    val genresText = genres.take(3).reduce { acc, s -> "$acc,$s"} + " ($year)"
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(95.dp)
            .wrapContentHeight()
            .combinedClickable(
                onClick = onClicked,
                onLongClick = onLongClicked
            ),
        backgroundColor = BackgroundWhite,
        shape = RoundedCornerShape(percent = 15),
        elevation = 10.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(15.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Card(
                shape = RoundedCornerShape(percent = 10),
            ) {
                Image(
                    painter = image,
                    contentScale = ContentScale.FillHeight,
                    contentDescription = "film picture"
                )
            }
            Spacer(modifier = Modifier.width(15.dp))
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = title,
                        fontFamily = FontFamily.Roboto,
                        fontWeight = FontWeight.Medium,
                        fontSize = 16.sp
                    )
                    if(isFavourite){
                        Icon(
                            painter = painterResource(id = R.drawable.ic_star),
                            tint = BackgroundBlue,
                            contentDescription = "favourite card"
                        )
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = genresText,
                    fontFamily = FontFamily.Roboto,
                    fontWeight = FontWeight.Medium,
                    color = TextBlack,
                    fontSize = 14.sp
                )
            }
    }

}
}

@Composable
fun LoadingWidget(
    modifier: Modifier = Modifier,
    text: String
){

}

@Preview
@Composable
fun MovieCard() {
    MovieCard(
        title = "Снегурочка",
        genres = listOf("Боевик", "Стратегия"),
        year = 1786,
        image = painterResource(id = R.drawable.film_main_picture),
        isFavourite = true,
        onClicked = {},
        onLongClicked = {})
}


@Preview
@Composable
fun ButtonPreview() {
    var clicked by remember {
        mutableStateOf(true)
    }
    if (clicked) {
        BlueToggleButton(
            text = "Избранное",
            onClicked = { clicked = !clicked },
            isChoosen = true
        )
    } else {
        BlueToggleButton(
            text = "Избранное",
            onClicked = { clicked = !clicked },
            isChoosen = false
        )
    }
}